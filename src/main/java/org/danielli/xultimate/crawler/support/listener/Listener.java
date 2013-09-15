package org.danielli.xultimate.crawler.support.listener;

/**
 * 监听器，用于处理特定页面的功能。
 * 
 * @author Daniel Li
 * @since 12 Jun 2013
 */
public interface Listener {

	boolean support(String linkUrl);
	
	void handle(String linkId, String linkUrl) throws Exception;
	
}
