package org.danielli.xultimate.crawler.alibaba.pipeline.dao;

import org.danielli.xultimate.crawler.alibaba.model.MemberFan;

public interface MemberFanDAO {
	
	/**
	 * 保存
	 */
	Long saveMemberFan(Integer shardId, MemberFan memberFan);
	
	/**
	 * 获取ID
	 */
	Long getIdByMemberIdAndFanMemberId(Integer shardId, String memberId, String fanMemberId);
	
	/**
	 * 创建
	 */
	void createMemberFanTable(Integer shardId, String memberId); 
}
