package org.danielli.xultimate.crawler2.support;

import org.danielli.xultimate.crawler2.StatusChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
