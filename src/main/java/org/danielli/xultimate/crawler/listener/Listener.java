package org.danielli.xultimate.crawler.listener;

public interface Listener {

	boolean support(String linkUrl);
	
	void handle(String linkId, String linkUrl);
	
}
