package org.danielli.xultimate.crawler.pipeline.dao;

import org.danielli.xultimate.crawler.model.Member;

public interface MemberDAO {

	/***
	 * 保存
	 */
	void saveMember(Integer shardId, Member member);
	
	/**
	 * 获取ID
	 */
	Long getIdByAlibabaId(Integer shardId, String alibabaId);
	
	/**
	 * 建表
	 */
	void createMemberTable(Integer shardId);
	
}
