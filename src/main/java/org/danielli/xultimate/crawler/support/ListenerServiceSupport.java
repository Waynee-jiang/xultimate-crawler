package org.danielli.xultimate.crawler.support;

import java.util.Arrays;
import java.util.Comparator;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.danielli.xultimate.crawler.LinkbaseService;
import org.danielli.xultimate.crawler.ListenerService;
import org.danielli.xultimate.crawler.support.listener.AbstractListener;
import org.danielli.xultimate.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("listenerService")
public class ListenerServiceSupport implements ListenerService {
	
	@Resource(name = "listeners")
	private AbstractListener[] listeners;
	
	@Resource(name = "linkbaseService")
	private LinkbaseService linkbaseService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ListenerServiceSupport.class);
	
	@PostConstruct
	public void init() {
		Assert.notEmpty(listeners);
		// 按优先级排序。
		Arrays.sort(listeners, new Comparator<AbstractListener>() {
			@Override
			public int compare(AbstractListener o1, AbstractListener o2) {
				return o2.getPriority() - o1.getPriority();
			}
		});
	}

	@Override
	public void execute(String linkId, String linkUrl) {
		LOGGER.info("开始处理链接{}:{}", linkId, linkUrl);
		int handleCount = 0;
		try {
			for (int i = listeners.length - 1; i >= 0; i--) {
				AbstractListener abstractListener = listeners[i];
				if (abstractListener.support(linkUrl)) {
					abstractListener.handle(linkId, linkUrl);
					handleCount++;
				}
			}
			
			if (handleCount == 0) {
				LOGGER.error("处理链接" + linkId + ":" + linkUrl + "失败: 没有监听器处理");
				linkbaseService.unableLinkId(linkId);
			} else {
				LOGGER.info("处理链接{}:{}成功", linkId, linkUrl);
				linkbaseService.completionLinkId(linkId);
			}
		} catch (Exception e) {
			LOGGER.error("处理链接" + linkId + ":" + linkUrl + "失败: " + e.getMessage(), e);
			linkbaseService.unableLinkId(linkId);
		}
		

	}
}
