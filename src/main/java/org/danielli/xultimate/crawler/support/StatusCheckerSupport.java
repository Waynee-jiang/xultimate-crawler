package org.danielli.xultimate.crawler.support;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.danielli.xultimate.crawler.StatusChecker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("statusChecker")
public class StatusCheckerSupport implements StatusChecker {

	@Value("${running.file.prefix}")
	private String prefix;
	
	@Value("${running.file.suffix}")
	private String suffix;
	
	private File runningFile;

	@PostConstruct
	public void init() throws IOException {
		runningFile = File.createTempFile(prefix, suffix);
	}

	@PreDestroy
	public void destroy() {
		runningFile.delete();
	}

	@Override
	public boolean getStatus() {
		return runningFile.exists();
	}
}
