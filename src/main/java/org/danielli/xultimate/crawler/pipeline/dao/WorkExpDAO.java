package org.danielli.xultimate.crawler.pipeline.dao;

import org.danielli.xultimate.crawler.model.WorkExp;

public interface WorkExpDAO {
	
	/**
	 * 保存
	 */
	Long saveWorkExp(WorkExp workExp);
}
