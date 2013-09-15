package org.danielli.xultimate.crawler.support.listener;

import javax.annotation.Resource;

import org.danielli.xultimate.crawler.LinkbaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractListener implements Listener {
	
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "linkbaseService")
	protected LinkbaseService linkbaseService;
	/**
	 * 获取任务优先级，优先级高的将优先判断。
	 * @return 优先级。
	 */
	public abstract int getPriority();
}
