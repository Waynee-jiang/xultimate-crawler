package org.danielli.xultimate.crawler2.support.urlresolver;

/**
 * URL解析器，用于处理特定页面的功能。
 * 
 * @author Daniel Li
 * @since 12 Jun 2013
 */
public interface UrlResolver {

	boolean support(String linkUrl);
	
	void handle(String linkId, String linkUrl) throws Exception;
	
}
