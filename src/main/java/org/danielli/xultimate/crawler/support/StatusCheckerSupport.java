package org.danielli.xultimate.crawler.support;

import org.danielli.xultimate.crawler.StatusChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 状态检测器默认实现类。
 * 
 * @author Daniel Li
 * @since 12 Jun 2013
 */
@Service("statusCheckerSupport")
public class StatusCheckerSupport implements StatusChecker {

	private volatile boolean running;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StatusCheckerSupport.class);

	public StatusCheckerSupport() {
		running = true;
		LOGGER.info("状态检测器已开启");
	}

	@Override
	public void closeStatus() {
		running = false;
		LOGGER.info("状态检测器已关闭");
	}

	@Override
	public boolean getStatus() {
		return running;
	}
}
