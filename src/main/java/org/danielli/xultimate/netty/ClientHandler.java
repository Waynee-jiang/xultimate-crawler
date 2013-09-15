package org.danielli.xultimate.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.danielli.xultimate.core.serializer.Deserializer;
import org.danielli.xultimate.core.serializer.DeserializerException;
import org.danielli.xultimate.core.serializer.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHandler extends SimpleChannelInboundHandler<byte[]> {

	private Serializer serializer;
	
	private Deserializer deserializer;

	private volatile Channel channel;
	
	private final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
	
	private final BlockingQueue<byte[]> answer = new LinkedBlockingQueue<byte[]>();
	
	public ClientHandler(Serializer serializer, Deserializer deserializer) {
		this.serializer = serializer;
		this.deserializer = deserializer;
	}
	
	public void publish(String message) {
		channel.writeAndFlush(serializer.serialize(message));
	}
	
	public String receive() {
		boolean interrupted = false;
        for (;;) {
            try {
            	byte[] factorial = answer.take();
                if (interrupted) {
                    Thread.currentThread().interrupt();
                }
                return deserializer.deserialize(factorial, String.class);
            } catch (InterruptedException | DeserializerException e) {
                interrupted = true;
            }
        }
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		channel = ctx.channel();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {
		answer.add(msg);
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
