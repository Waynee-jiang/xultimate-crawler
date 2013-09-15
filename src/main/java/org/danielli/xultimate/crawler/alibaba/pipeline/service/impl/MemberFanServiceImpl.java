package org.danielli.xultimate.crawler.alibaba.pipeline.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.danielli.xultimate.crawler.alibaba.model.MemberFan;
import org.danielli.xultimate.crawler.alibaba.pipeline.dao.MemberFanDAO;
import org.danielli.xultimate.crawler.alibaba.pipeline.dao.MemberShardDAO;
import org.danielli.xultimate.crawler.alibaba.pipeline.service.MemberFanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("alibabaMemberFanService")
public class MemberFanServiceImpl implements MemberFanService {

	@Resource(name = "alibabaMemberFanDAO")
	private MemberFanDAO memberFanDAO;
	
	@Resource(name = "alibabaMemberShardDAO")
	private MemberShardDAO memberShardDAO;
	
	@Override
	@Transactional(value = "alibabaTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
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
