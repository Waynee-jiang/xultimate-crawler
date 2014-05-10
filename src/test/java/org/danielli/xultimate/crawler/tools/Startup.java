package org.danielli.xultimate.crawler.tools;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.LineIterator;
import org.danielli.xultimate.context.util.ApplicationContextUtils;
import org.danielli.xultimate.crawler.LinkbaseHandler;
import org.danielli.xultimate.crawler.support.LinkbaseHandlerSupport;
import org.danielli.xultimate.util.ArrayUtils;
import org.danielli.xultimate.util.CharsetUtils.CharEncoding;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.collections.CollectionUtils;
import org.danielli.xultimate.util.io.FileUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动器。
 * 
 * @author Daniel Li
 * @since 12 Jun 2013
 */
public class Startup {
	public static void main(String[] args) throws IOException {
		if (ArrayUtils.isNotEmpty(args) && args.length != 1) {
			System.err.println("用法: Startup [urlFile]");
			System.exit(1);
		}
		
		List<Map<String, Object>> linkUrls = new ArrayList<Map<String, Object>>();
		if (ArrayUtils.isNotEmpty(args) && StringUtils.isNotBlank(args[0])) {
			LineIterator lineIterator = FileUtils.lineIterator(new File(args[0]), CharEncoding.UTF_8);
			try {
				while (lineIterator.hasNext()) {
					String url = lineIterator.next();
					if (StringUtils.isNotBlank(url)) {
						Map<String, Object> linkUrlMap = new HashMap<>();
						linkUrlMap.put(LinkbaseHandlerSupport.LINK_COLUMN_NAME, url);
						linkUrlMap.put(LinkbaseHandlerSupport.LINK_CREATE_TIME, System.currentTimeMillis());
						linkUrls.add(linkUrlMap);
					}
				}
			} finally {
				LineIterator.closeQuietly(lineIterator);
			}
		}
		
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:crawler/applicationContext-service-crawler-*.xml", "classpath*:crawler/applicationContext-*-*.xml", "classpath:monitor/applicationContext-service-netty-server.xml");
		if (CollectionUtils.isNotEmpty(linkUrls)) {
			LinkbaseHandler linkbaseService = ApplicationContextUtils.getBean(applicationContext, LinkbaseHandler.class);
			linkbaseService.addLinkUrls(linkUrls);
		}
	}
}
