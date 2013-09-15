package org.danielli.xultimate.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.apache.commons.lang3.StringUtils;
import org.danielli.xultimate.core.serializer.Deserializer;
import org.danielli.xultimate.core.serializer.Serializer;
import org.danielli.xultimate.crawler.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * 提供了各种可以覆盖的事件处理方法。
 * 
 * @author toc
 * 
 */
public class ServerHandler extends SimpleChannelInboundHandler<byte[]> implements ApplicationContextAware {

	private Serializer serializer;
	
	private Deserializer deserializer;
	
	private ChannelHandlerContext handlerContext;
	
	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void publish(String message) {
		handlerContext.writeAndFlush(serializer.serialize(message));
	}
	
	public void close() {
		publish("ok");
		handlerContext.close();
		((AbstractApplicationContext) applicationContext).close();
	}
	
	private final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
	
	public ServerHandler(Serializer serializer, Deserializer deserializer) {
		this.serializer = serializer;
		this.deserializer = deserializer;
	}
	
	/**
	 * 当来自客户端的新数据被接收时，该方法被调用。msg为接收到的消息。
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {
		String message = deserializer.deserialize(msg, String.class);
		if (StringUtils.equals("shutdown", message)) {
			this.handlerContext = ctx;
			new Thread(new Server.Close(this, applicationContext)).start();
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("Unexpected exception from downstream", cause);
		ctx.close(); // 关闭连接。
	}
}
