package org.danielli.xultimate.crawler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;

import java.util.concurrent.Semaphore;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.danielli.xultimate.crawler.support.ListenerServiceSupport;
import org.danielli.xultimate.netty.ServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * 服务器。
 * 
 * @author Daniel Li
 * @since 12 Jun 2013
 */
@Service("server")
@Lazy(false)
public class Server implements ApplicationContextAware {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
	
	@Resource(name = "threadPoolTaskExecutor")
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Resource(name = "listenerService")
	private ListenerServiceSupport listenerService;
	
	@Resource(name = "linkbaseService")
	private LinkbaseService linkbaseService;
	
	@Resource(name = "statusChecker")
	private StatusChecker statusChecker;
	
	private ApplicationContext applicationContext;
	
	@Value("${server.port}")
	private int port;
	
	@Value("${server.concurrency}")
	private int concurrency = -1;
	
	/**
	 * 限制总并发为concurrency个。
	 */
	private Semaphore semaphore;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	@PostConstruct
	public void init() {
		if (concurrency > 0)
			semaphore = new Semaphore(concurrency);
		new Thread(new Acceptor()).start();
		new Thread(new Open(applicationContext, port)).start();
		LOGGER.info("服务器启动成功");
	}
	
	private class Acceptor implements Runnable {
		
		@Override
		public void run() {
			while (statusChecker.getStatus()) {
				try {
					String linkId = linkbaseService.getLinkId();
					if (linkId == null) {
						LOGGER.warn("队列已经为空，无法获取新链接");
						Thread.sleep(20 * 1000);
						continue;
					}
					threadPoolTaskExecutor.execute(new LinkProcessor(linkId, linkbaseService.getLinkUrlByLinkId(linkId)));
					Thread.sleep(10 * 1000);
				} catch (Exception e) {
					LOGGER.error("获取新链接失败：" + e.getMessage(), e);
				}
			}
		}
	}
	
	public class Open implements Runnable {
		
		private ApplicationContext applicationContext;
		
		private int port;
		
		public Open(ApplicationContext applicationContext, int port) {
			this.applicationContext = applicationContext;
			this.port = port;
		}
		
		@SuppressWarnings("resource")
		@Override
		public void run() {
			try {
				ClassPathXmlApplicationContext nettyApplicationContext = new ClassPathXmlApplicationContext("classpath:/applicationContext-service-netty-server.xml", "classpath:/applicationContext-service-serializer.xml");
				nettyApplicationContext.setParent(applicationContext);
				ServerBootstrap serverBootstrap = nettyApplicationContext.getBean(ServerBootstrap.class);
				Channel channel = serverBootstrap.bind(port).sync().channel();	// 绑定端口并运行。
				channel.closeFuture().sync();
			} catch (InterruptedException e) {
				LOGGER.info("服务器启动失败", e);
				((AbstractApplicationContext) applicationContext).close();
			} 				
    		
		}
	}
	
	public static class Close implements Runnable {
		
		private ServerHandler serverHandler;
		
		private ApplicationContext applicationContext;
		
		public Close(ServerHandler serverHandler, ApplicationContext applicationContext) {
			this.serverHandler = serverHandler;
			this.applicationContext = applicationContext;
		}
		
		@Override
		public void run() {
			try {
				serverHandler.publish("收到通知，开始关闭...");
				((AbstractApplicationContext) applicationContext.getParent()).close();
				serverHandler.publish("关闭成功...");
				LOGGER.info("服务器关闭成功");
			} catch (Exception e) {
				serverHandler.publish("关闭失败...");
				e.printStackTrace();
				serverHandler.publish("服务器关闭失败" + e.getMessage());
			} finally {
				serverHandler.close();
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
				LOGGER.error("未知错误：" + e.getMessage(), e);
			} finally {
				if (semaphore != null) {
					semaphore.release();
				}
			}	
		}
	}
}
