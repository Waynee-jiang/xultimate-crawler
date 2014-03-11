package org.danielli.xultimate.crawler2.support;

import java.util.Arrays;
import java.util.Comparator;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.danielli.xultimate.crawler2.LinkbaseHandler;
import org.danielli.xultimate.crawler2.UrlResolveExecutor;
import org.danielli.xultimate.crawler2.support.urlresolver.AbstractUrlResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("urlResolveExecutorSupport")
public class UrlResolveExecutorSupport implements UrlResolveExecutor {
	
	@Resource(name = "UrlResolverCollection")
	private AbstractUrlResolver[] abstractUrlResolvers;
	
	@Resource(name = "linkbaseHandlerSupport")
	private LinkbaseHandler linkbaseHandler;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UrlResolveExecutorSupport.class);
	
	@PostConstruct
	public void init() {
		if (ArrayUtils.isNotEmpty(abstractUrlResolvers)) {
			// 按优先级排序。
			Arrays.sort(abstractUrlResolvers, new Comparator<AbstractUrlResolver>() {
				@Override
				public int compare(AbstractUrlResolver o1, AbstractUrlResolver o2) {
					return o2.getPriority() - o1.getPriority();
				}
			});
		}
	}

	private AbstractUrlResolver getUrlResolver(String linkUrl) {
		for (int i = abstractUrlResolvers.length - 1; i >= 0; i--) {
			AbstractUrlResolver abstractListener = abstractUrlResolvers[i];
			if (abstractListener.support(linkUrl)) {
				return abstractListener;
			}
		}
		return null;
	}
	
	@Override
	public void execute(String linkId, String linkUrl) {
		LOGGER.info("准备处理链接{}:{}", linkId, linkUrl);
		try {
			AbstractUrlResolver abstractUrlResolver = getUrlResolver(linkUrl);
			if (abstractUrlResolver == null) {
				LOGGER.warn("处理链接{}:{}失败: 没有监听器处理", linkId, linkUrl);
				linkbaseHandler.unableLinkId(linkId);
			} else {
				abstractUrlResolver.handle(linkId, linkUrl);
				LOGGER.info("处理链接{}:{}成功", linkId, linkUrl);
				linkbaseHandler.completionLinkId(linkId);
			}
		} catch (Exception e) {
			LOGGER.error("处理链接" + linkId + ":" + linkUrl + "失败: " + e.getMessage(), e);
			linkbaseHandler.unableLinkId(linkId);
		}
	}
}
