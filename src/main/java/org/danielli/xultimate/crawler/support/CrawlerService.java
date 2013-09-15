package org.danielli.xultimate.crawler.support;

import java.io.File;
import java.util.concurrent.Semaphore;

import org.danielli.xultimate.crawler.LinkbaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class CrawlerService implements InitializingBean {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerService.class);
	
	private ThreadPoolTaskExecutor taskExecutor;

	/** 任务处理器 */
	private ListenerService listenerService;	/** 任务处理器 */
	
	private LinkbaseService linkBaseService;
	
	private File runningFile;
	
	private int concurrency = -1;
	
	/**
	 * 限制总并发为concurrency个。
	 */
	private Semaphore semaphore;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		if (concurrency > 0)
			semaphore = new Semaphore(concurrency);
		Thread thread = new Thread(new Acceptor());
		thread.setPriority(Thread.MAX_PRIORITY);
		thread.setDaemon(true);
		thread.start();
	}
	
	private class Acceptor implements Runnable {
		
		@Override
		public void run() {
			while (runningFile.exists()) {
				try {
					String linkId = linkBaseService.getLinkId();
					if (linkId == null) {
						Thread.sleep(20 * 1000);
					}
					taskExecutor.execute(new LinkProcessor(linkId, linkBaseService.getLinkUrlByLinkId(linkId)));
				} catch (TaskRejectedException e) { 	// 这个异常不能改
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
				
			}
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
				listenerService.execute(linkId, linkUrl);
			} catch (Exception e) {
				
			} finally {
				if (semaphore != null) {
					semaphore.release();
				}
			}	
		}
	}
}
