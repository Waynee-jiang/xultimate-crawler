package org.danielli.xultimate.crawler.support.urlresolver;

import javax.annotation.Resource;

import org.danielli.xultimate.crawler.LinkbaseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象URL解析器，添加优先级功能。
 * 
 * @author Daniel Li
 * @since 12 Jun 2013
 */
public abstract class AbstractUrlResolver implements UrlResolver {
	
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "linkbaseHandlerSupport")
	protected LinkbaseHandler linkbaseHandler;
	/**
	 * 获取任务优先级，优先级高的将优先判断。
	 * @return 优先级。
	 */
	public abstract int getPriority();
}
