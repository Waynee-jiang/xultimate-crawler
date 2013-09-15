package org.danielli.xultimate.crawler.tools;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;

import java.io.IOException;

import org.danielli.xultimate.netty.ClientHandler;
import org.danielli.xultimate.util.ArrayUtils;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.math.NumberUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 终止器。
 * 
 * @author Daniel Li
 * @since 12 Jun 2013
 */
public class Shutdown {
	public static void main(String[] args) throws IOException {
		if (!ArrayUtils.isNotEmpty(args) || args.length != 2) {
			System.err.println("用法: Shutdown ip port");
			System.exit(1);
		}
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/applicationContext-service-netty-client.xml", "classpath:/applicationContext-service-serializer.xml");
		try {
			Bootstrap bootstrap = applicationContext.getBean(Bootstrap.class);
			Channel channel = bootstrap.connect(args[0], NumberUtils.createInteger(args[1])).sync().channel();
			ClientHandler clientHandler = applicationContext.getBean(ClientHandler.class);
			clientHandler.publish("shutdown");
			while (true) {
				String result = clientHandler.receive();
				System.out.println(result);
				if (StringUtils.equals("ok", result)) {
					break;
				}
			}
			channel.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			applicationContext.close();
		}
	}
}