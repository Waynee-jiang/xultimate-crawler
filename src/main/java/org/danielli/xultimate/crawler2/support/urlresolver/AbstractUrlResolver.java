package org.danielli.xultimate.crawler2.support.urlresolver;

import javax.annotation.Resource;

import org.danielli.xultimate.crawler2.LinkbaseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
