package org.danielli.xultimate.crawler2.monitor;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.apache.commons.lang3.StringUtils;
import org.danielli.xultimate.core.serializer.Deserializer;
import org.danielli.xultimate.core.serializer.Serializer;
import org.danielli.xultimate.crawler2.StatusChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 提供了各种可以覆盖的事件处理方法。
 * 
 * @author toc
 * 
 */
public class ServerHandler extends SimpleChannelInboundHandler<byte[]> {

	private Serializer serializer;
	private Deserializer deserializer;
	private StatusChecker statusChecker;
	
	private ChannelHandlerContext handlerContext;

	public ChannelFuture publish(String message) {
		return handlerContext.writeAndFlush(serializer.serialize(message));
	}
	
	private final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
	
	public ServerHandler(Serializer serializer, Deserializer deserializer, StatusChecker statusChecker) {
		this.serializer = serializer;
		this.deserializer = deserializer;
		this.statusChecker = statusChecker;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		handlerContext = ctx;
	}
	
	/**
	 * 当来自客户端的新数据被接收时，该方法被调用。msg为接收到的消息。
	 */
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, byte[] msg) throws Exception {
		String message = deserializer.deserialize(msg, String.class);
		if (StringUtils.equals("shutdown", message)) {
			publish("收到通知，审核关闭...");
			if (statusChecker.getStatus()) {
				statusChecker.closeStatus();
				publish("已打开关闭信号，应用稍后自动关闭...");
			} else {
				publish("关闭信号已经开启，请不要重复操作，应用正在关闭中...");
			}
			final ChannelFuture channelFuture = publish("ok");
			channelFuture.addListener(ChannelFutureListener.CLOSE);
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
