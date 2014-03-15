package org.danielli.xultimate.crawler.monitor;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;

import org.danielli.xultimate.context.util.BeanFactoryContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * Shutdown命令的监听线程。
 * 
 * @author Daniel Li
 * @since 12 Jun 2013
 */
public class ShutdownMonitorThread extends Thread {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShutdownMonitorThread.class);
	/** 监听端口 */
	private int port;
	/** 服务启动器 */
	private ServerBootstrap serverBootstrap;
	
	public ShutdownMonitorThread(ServerBootstrap serverBootstrap, int port) {
		this.serverBootstrap = serverBootstrap;
		this.port = port;
	}
	
	@Override
	public void run() {
		Channel channel = null;
		try {
			channel = serverBootstrap.bind(port).sync().channel();	// 绑定端口并运行。
			LOGGER.info("监听线程已启动");
		} catch (InterruptedException e) {
			LOGGER.error("监听线程启动失败");
			((AbstractApplicationContext) BeanFactoryContext.currentApplicationContext()).close();
		} 
		
		try {
			channel.closeFuture().sync();
			LOGGER.info("监听线程已关闭");
		} catch (InterruptedException e) {
			LOGGER.error("监听线程启动失败");
		} 
	}
}
