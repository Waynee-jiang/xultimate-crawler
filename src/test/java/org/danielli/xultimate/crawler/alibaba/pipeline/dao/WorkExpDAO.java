package org.danielli.xultimate.crawler.alibaba.pipeline.dao;

import org.danielli.xultimate.crawler.alibaba.model.WorkExp;

public interface WorkExpDAO {
	
	/**
	 * 保存
	 */
	Long saveWorkExp(WorkExp workExp);
}
