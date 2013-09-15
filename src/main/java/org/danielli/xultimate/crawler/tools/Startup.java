package org.danielli.xultimate.crawler.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.danielli.xultimate.crawler.LinkbaseService;
import org.danielli.xultimate.crawler.support.LinkbaseServiceSupport;
import org.danielli.xultimate.util.ArrayUtils;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.collections.CollectionUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动器。
 * 
 * @author Daniel Li
 * @since 12 Jun 2013
 */
public class Startup {
	public static void main(String[] args) {
		if (ArrayUtils.isNotEmpty(args) && args.length != 1) {
			System.err.println("用法: Startup [urlFile]");
			System.exit(1);
		}
		
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:applicationContext-service-crawler*.xml");
		
		if (ArrayUtils.isNotEmpty(args)) {
			String urlFileStr = args[0];
			if (StringUtils.isNotBlank(urlFileStr)) {
				Scanner scanner = null;
				List<Map<String, Object>> linkUrls = new ArrayList<Map<String, Object>>();
				try {
					scanner = new Scanner(new File(urlFileStr));;
					while (scanner.hasNextLine()) {
						Map<String, Object> linkUrlMap = new HashMap<>();
						linkUrlMap.put(LinkbaseServiceSupport.LINK_COLUMN_NAME, scanner.nextLine());
						linkUrlMap.put(LinkbaseServiceSupport.LINK_CREATE_TIME, System.currentTimeMillis());
						linkUrls.add(linkUrlMap);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					scanner.close();
				}
				if (CollectionUtils.isNotEmpty(linkUrls)) {
					LinkbaseService linkbaseService = applicationContext.getBean("linkbaseService", LinkbaseService.class);
					linkbaseService.addLinkUrls(linkUrls);
				}
			}
		}
	}
}
