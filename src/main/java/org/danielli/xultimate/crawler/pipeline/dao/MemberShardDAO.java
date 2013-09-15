package org.danielli.xultimate.crawler.pipeline.dao;

import org.danielli.xultimate.crawler.model.MemberShard;

public interface MemberShardDAO {
	
	/**
	 * 保存
	 */
	void saveMemberShard(MemberShard memberShard);
	
	/**
	 * 获取标识
	 */
	Integer getShardIdByMemberId(String memberId);
	
}
