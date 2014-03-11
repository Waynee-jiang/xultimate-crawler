package org.danielli.xultimate.net.netty.support;

import java.util.Map;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.danielli.xultimate.util.collections.MapUtils;
import org.springframework.beans.factory.FactoryBean;

public class ServerBootstrapFactoryBean implements FactoryBean<ServerBootstrap> {

	private EventLoopGroup parentGroup;
	
	private EventLoopGroup childGroup;
	
	private ChannelHandler channelHandler;
	
	private Map<ChannelOption<Object>, Object> options;
	
	private Map<ChannelOption<Object>, Object> childOptions;
	
	@Override
	public ServerBootstrap getObject() throws Exception {
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(parentGroup, childGroup);
		serverBootstrap.channel(NioServerSocketChannel.class);		// 被用来实例化一个新的Channel来接受传传入的连接。
		
		if (MapUtils.isNotEmpty(options)) {
			for (Map.Entry<ChannelOption<Object>, Object> entry : options.entrySet()) {
				serverBootstrap.option(entry.getKey(), entry.getValue());
			}
		}
		
		if (MapUtils.isNotEmpty(childOptions)) {
			for (Map.Entry<ChannelOption<Object>, Object> entry : childOptions.entrySet()) {
				serverBootstrap.childOption(entry.getKey(), entry.getValue());
			}
		}
		
		serverBootstrap.childHandler(channelHandler);
		return serverBootstrap;
	}

	@Override
	public Class<?> getObjectType() {
		return ServerBootstrap.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public void setParentGroup(EventLoopGroup parentGroup) {
		this.parentGroup = parentGroup;
	}

	public void setChildGroup(EventLoopGroup childGroup) {
		this.childGroup = childGroup;
	}

	public void setChannelHandler(ChannelHandler channelHandler) {
		this.channelHandler = channelHandler;
	}

	public void setOptions(Map<ChannelOption<Object>, Object> options) {
		this.options = options;
	}

	public void setChildOptions(Map<ChannelOption<Object>, Object> childOptions) {
		this.childOptions = childOptions;
	}

}
