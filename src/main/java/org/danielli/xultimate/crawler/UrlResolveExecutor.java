package org.danielli.xultimate.crawler;

/**
 * URL解析执行器。
 * 
 * @author Daniel Li
 * @since 12 Jun 2013
 */
public interface UrlResolveExecutor {
	
	void execute(String linkId, String linkUrl);
}
