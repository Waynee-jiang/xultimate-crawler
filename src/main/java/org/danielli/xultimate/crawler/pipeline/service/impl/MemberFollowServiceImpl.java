package org.danielli.xultimate.crawler.pipeline.service.impl;

import java.util.Date;

import org.danielli.xultimate.crawler.model.MemberFollow;
import org.danielli.xultimate.crawler.pipeline.dao.MemberFollowDAO;
import org.danielli.xultimate.crawler.pipeline.dao.MemberShardDAO;
import org.danielli.xultimate.crawler.pipeline.service.MemberFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberFollowServiceImpl implements MemberFollowService {

	@Autowired
	private MemberFollowDAO memberFollowDAO;
	
	@Autowired
	private MemberShardDAO memberShardDAO;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
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
