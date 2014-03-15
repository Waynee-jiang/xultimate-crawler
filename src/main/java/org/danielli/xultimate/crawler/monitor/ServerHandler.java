package org.danielli.xultimate.crawler.monitor;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.apache.commons.lang3.StringUtils;
import org.danielli.xultimate.crawler.StatusChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Netty的服务端处理器，提供了各种可以覆盖的事件处理方法。
 * 
 * @author Daniel Li
 * @since 12 Jun 2013
 */
@Sharable
public class ServerHandler extends SimpleChannelInboundHandler<Object> {

	private StatusChecker statusChecker;
	
	private final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
	
	public ServerHandler(StatusChecker statusChecker) {
		this.statusChecker = statusChecker;
	}
	
	/**
	 * 当来自客户端的新数据被接收时，该方法被调用。msg为接收到的消息。
	 */
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
		String message = msg.toString();
		if (StringUtils.equals("shutdown", message)) {
			ctx.writeAndFlush("收到通知，审核关闭...");
			if (statusChecker.getStatus()) {
				statusChecker.closeStatus();
				ctx.writeAndFlush("已打开关闭信号，应用稍后自动关闭...");
			} else {
				ctx.writeAndFlush("关闭信号已经开启，请不要重复操作，应用正在关闭中...");
			}
			final ChannelFuture channelFuture = ctx.writeAndFlush("ok");
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
