package org.danielli.xultimate.crawler.monitor;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;

import org.danielli.xultimate.context.util.BeanFactoryContext;
import org.danielli.xultimate.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * Netty的客户端处理器。
 * 
 * @author Daniel Li
 * @since 12 Jun 2013
 */
@Sharable
public class ClientHandler extends SimpleChannelInboundHandler<Object> {

	private final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
	
//	private volatile Channel channel;
//	
//	private final BlockingQueue<byte[]> answer = new LinkedBlockingQueue<byte[]>();
//	
//	public void publish(String message) {
//		channel.writeAndFlush(serializer.serialize(message));
//	}
//	
//	public String receive() {
//		boolean interrupted = false;
//        for (;;) {
//            try {
//            	byte[] factorial = answer.take();
//                if (interrupted) {
//                    Thread.currentThread().interrupt();
//                }
//                return deserializer.deserialize(factorial, String.class);
//            } catch (InterruptedException | DeserializerException e) {
//                interrupted = true;
//            }
//        }
//	}
	
//	@Override
//	public void channelActive(ChannelHandlerContext ctx) {
//		channel = ctx.channel();
//	}
	
//	@Override
//	protected void messageReceived(ChannelHandlerContext ctx, byte[] msg) throws Exception {
//		answer.add(msg);
//	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush("shutdown");
	}
	
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
		String message = msg.toString();
		System.out.println(message);
		if (StringUtils.equals("ok", message)) {
			ctx.close();
			((AbstractApplicationContext) BeanFactoryContext.currentApplicationContext()).close();
		}
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.error("Unexpected exception from downstream", cause);
		ctx.close();
	}
}
