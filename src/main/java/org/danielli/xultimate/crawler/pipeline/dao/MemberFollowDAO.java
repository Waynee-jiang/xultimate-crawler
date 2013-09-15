package org.danielli.xultimate.crawler.pipeline.dao;

import org.danielli.xultimate.crawler.model.MemberFollow;

public interface MemberFollowDAO {
	
	/**
	 * 保存
	 */
	Long saveMemberFollow(Integer shardId, MemberFollow memberFollow);
	
	/**
	 * 获取ID
	 */
	Long getIdByMemberIdAndFollowMemberId(Integer shardId, String memberId, String followMemberId);
	
	/**
	 * 创建
	 */
	void createMemberFollowTable(Integer shardId, String memberId);
}
