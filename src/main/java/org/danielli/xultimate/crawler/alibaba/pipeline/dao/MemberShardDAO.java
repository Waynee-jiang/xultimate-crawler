package org.danielli.xultimate.crawler.alibaba.pipeline.dao;

import org.danielli.xultimate.crawler.alibaba.model.MemberShard;

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
