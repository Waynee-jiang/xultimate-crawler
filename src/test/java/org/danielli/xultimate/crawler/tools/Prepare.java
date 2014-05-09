package org.danielli.xultimate.crawler.tools;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 初始化工具，用于创建ID生成器表、shard规则表、抓取表。
 * 
 * @author Daniel Li
 * @since 08 May 2014
 */
public class Prepare {
	public static void main(String[] args) {
		String[] configLocations = { 
				"crawler/applicationContext-service-config.xml",
				"crawler/applicationContext-service-crypto.xml",
				"alibaba/applicationContext-dao-base.xml",
				"alibaba/applicationContext-dao-primary-init.xml", 
				"alibaba/applicationContext-dao-shard-init.xml",
				"alibaba/applicationContext-dao-table-init.xml"
				};
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(configLocations);
		applicationContext.close();
	}
}
