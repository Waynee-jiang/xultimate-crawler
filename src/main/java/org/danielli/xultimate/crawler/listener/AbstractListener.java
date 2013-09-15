package org.danielli.xultimate.crawler.listener;

import org.danielli.xultimate.crawler.LinkbaseService;
import org.danielli.xultimate.util.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractListener implements Listener {
	
	private RestTemplate restTemplate;
	
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	protected LinkbaseService linkbaseService;
	
	@Override
	public void handle(String linkId, String linkUrl) {
		String html = restTemplate.getForObject(linkUrl, String.class);
		if (StringUtils.isNotEmpty(html)) {
			handle(Jsoup.parse(html), linkId, linkUrl);
		} else {
			throw new RuntimeException();
		}
	}
	
	/**
	 * 获取任务优先级，优先级高的将优先判断。
	 * @return 优先级。
	 */
	public abstract int getPriority();
	
	public abstract void handle(Document document, String linkId, String linkUrl);
}
