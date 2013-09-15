package org.danielli.xultimate.crawler.alibaba.pipeline.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.danielli.xultimate.crawler.alibaba.model.MemberFollow;
import org.danielli.xultimate.crawler.alibaba.pipeline.dao.MemberFollowDAO;
import org.danielli.xultimate.crawler.alibaba.pipeline.dao.MemberShardDAO;
import org.danielli.xultimate.crawler.alibaba.pipeline.service.MemberFollowService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("alibabaMemberFollowService")
public class MemberFollowServiceImpl implements MemberFollowService {

	@Resource(name = "alibabaMemberFollowDAO")
	private MemberFollowDAO memberFollowDAO;
	
	@Resource(name = "alibabaMemberShardDAO")
	private MemberShardDAO memberShardDAO;
	
	@Override
	@Transactional(value = "alibabaTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void saveMemberFollow(MemberFollow memberFollow) {
		Integer shardId = memberShardDAO.getShardIdByMemberId(memberFollow.getMemberId());
		Long id = memberFollowDAO.getIdByMemberIdAndFollowMemberId(shardId, memberFollow.getMemberId(), memberFollow.getFollowMemberId());
		if (id == null) {
			memberFollow.setCreateTime(new Date());
			id = memberFollowDAO.saveMemberFollow(shardId, memberFollow);
		}
		memberFollow.setId(id);
	}

}
