package org.danielli.xultimate.crawler2;

import java.util.concurrent.Semaphore;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.danielli.xultimate.context.util.BeanFactoryContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * URL解析调度器。
 * 
 * @author Daniel Li
 * @since 12 Jun 2013
 */
@Service("urlResolveScheduler")
@Lazy(false)
public class UrlResolveScheduler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UrlResolveScheduler.class);
	
	@Resource(name = "threadPoolTaskExecutor")
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Resource(name = "urlResolveExecutorSupport")
	private UrlResolveExecutor urlResolveExecutor ;
	
	@Resource(name = "linkbaseHandlerSupport")
	private LinkbaseHandler linkbaseHandler;
	
	@Resource(name = "statusCheckerSupport")
	private StatusChecker statusChecker;

	/**
	 * 限制总并发为concurrency个。
	 */
	@Value("${server.concurrency}")
	private int concurrency = -1;
	private Semaphore semaphore;
	
	@PostConstruct
	public void init() {
		if (concurrency > 0) semaphore = new Semaphore(concurrency);
		Thread thread = new Thread(new Acceptor());
		thread.setDaemon(true);
		thread.start();
	}

	private class Acceptor implements Runnable {
		
		@Override
		public void run() {
			LOGGER.info("URL解析调度器启动成功");
			while (statusChecker.getStatus()) {
				try {
					String linkId = linkbaseHandler.getLinkId();
					if (linkId == null) {
						LOGGER.warn("队列已经为空，无法获取新链接");
						Thread.sleep(20 * 1000);
						continue;
					}
					threadPoolTaskExecutor.execute(new LinkProcessor(linkId, linkbaseHandler.getLinkUrlByLinkId(linkId)));
					Thread.sleep(10 * 1000);
				} catch (Exception e) {
					LOGGER.error("获取新链接失败：" + e.getMessage(), e);
				}
			}
			LOGGER.info("URL解析调度器试图关闭");
			((AbstractApplicationContext) BeanFactoryContext.currentApplicationContext()).close();
		}
	}
	
	private class LinkProcessor implements Runnable {
		
		private String linkId;
		private String linkUrl;
		
		public LinkProcessor(String linkId, String linkUrl) {
			this.linkId = linkId;
			this.linkUrl = linkUrl;
		}

		@Override
		public void run() {
			try {
				if (semaphore != null) {
					semaphore.acquire();
				}
				urlResolveExecutor.execute(linkId, linkUrl);
			} catch (Exception e) {
				LOGGER.error("未知错误：" + e.getMessage(), e);
			} finally {
				if (semaphore != null) {
					semaphore.release();
				}
			}	
		}
	}
}
