package org.danielli.xultimate.crawler;

/**
 * 监听器服务。
 * 
 * @author Daniel Li
 * @since 12 Jun 2013
 */
public interface ListenerService {
	
	void execute(String linkId, String linkUrl);
}
