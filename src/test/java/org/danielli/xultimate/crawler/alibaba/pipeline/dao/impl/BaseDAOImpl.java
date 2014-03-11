package org.danielli.xultimate.crawler.alibaba.pipeline.dao.impl;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;

public abstract class BaseDAOImpl {
	
	@Resource(name = "alibabaJdbcTemplate")
	private JdbcTemplate alibabaJdbcTemplate;

	public JdbcTemplate getAlibabaJdbcTemplate() {
		return alibabaJdbcTemplate;
	}
}
