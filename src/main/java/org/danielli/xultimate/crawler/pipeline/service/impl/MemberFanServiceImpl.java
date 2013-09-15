package org.danielli.xultimate.crawler.pipeline.service.impl;

import java.util.Date;

import org.danielli.xultimate.crawler.model.MemberFan;
import org.danielli.xultimate.crawler.pipeline.dao.MemberFanDAO;
import org.danielli.xultimate.crawler.pipeline.dao.MemberShardDAO;
import org.danielli.xultimate.crawler.pipeline.service.MemberFanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberFanServiceImpl implements MemberFanService {

	@Autowired
	private MemberFanDAO memberFanDAO;
	
	@Autowired
	private MemberShardDAO memberShardDAO;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void saveMemberFan(MemberFan memberFan) {
		Integer shardId = memberShardDAO.getShardIdByMemberId(memberFan.getMemberId());
		Long id = memberFanDAO.getIdByMemberIdAndFanMemberId(shardId, memberFan.getMemberId(), memberFan.getFanMemberId());
		if (id == null) {
			memberFan.setCreateTime(new Date());
			id = memberFanDAO.saveMemberFan(shardId, memberFan);
		}
		memberFan.setId(id);
	}

}
