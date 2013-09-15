package org.danielli.xultimate.crawler.alibaba.pipeline.dao;

import org.danielli.xultimate.crawler.alibaba.model.ImpressLabel;

public interface ImpressLabelDAO {
	
	/**
	 * 保存
	 */
	Long saveImpressLabel(ImpressLabel impressLabel);
	
	/**
	 * 获取ID
	 */
	Long getIdByLabelName(String labelName);
}
