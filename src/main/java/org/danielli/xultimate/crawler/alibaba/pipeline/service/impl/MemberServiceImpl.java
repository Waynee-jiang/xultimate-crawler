package org.danielli.xultimate.crawler.alibaba.pipeline.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.danielli.xultimate.crawler.alibaba.model.EduExp;
import org.danielli.xultimate.crawler.alibaba.model.ImpressLabel;
import org.danielli.xultimate.crawler.alibaba.model.Member;
import org.danielli.xultimate.crawler.alibaba.model.MemberImpressLabel;
import org.danielli.xultimate.crawler.alibaba.model.MemberShard;
import org.danielli.xultimate.crawler.alibaba.model.WorkExp;
import org.danielli.xultimate.crawler.alibaba.pipeline.dao.EduExpDAO;
import org.danielli.xultimate.crawler.alibaba.pipeline.dao.ImpressLabelDAO;
import org.danielli.xultimate.crawler.alibaba.pipeline.dao.MemberDAO;
import org.danielli.xultimate.crawler.alibaba.pipeline.dao.MemberFanDAO;
import org.danielli.xultimate.crawler.alibaba.pipeline.dao.MemberFollowDAO;
import org.danielli.xultimate.crawler.alibaba.pipeline.dao.MemberImpressLabelDAO;
import org.danielli.xultimate.crawler.alibaba.pipeline.dao.MemberShardDAO;
import org.danielli.xultimate.crawler.alibaba.pipeline.dao.WorkExpDAO;
import org.danielli.xultimate.crawler.alibaba.pipeline.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("alibabaMemberService")
public class MemberServiceImpl implements MemberService {

	@Resource(name = "alibabaEduExpDAO")
	private EduExpDAO eduExpDAO;
	
	@Resource(name = "alibabaWorkExpDAO")
	private WorkExpDAO workExpDAO;
	
	@Resource(name = "alibabaMemberDAO")
	private MemberDAO memberDAO;
	
	@Resource(name = "alibabaMemberShardDAO")
	private MemberShardDAO memberShardDAO;

	@Resource(name = "alibabaImpressLabelDAO")
	private ImpressLabelDAO impressLabelDAO;

	@Resource(name = "alibabaMemberImpressLabelDAO")
	private MemberImpressLabelDAO memberImpressLabelDAO;
	
	@Resource(name = "alibabaMemberFanDAO")
	private MemberFanDAO memberFanDAO;
	
	@Resource(name = "alibabaMemberFollowDAO")
	private MemberFollowDAO memberFollowDAO;
	
	@Resource(name = "alibabaPrimaryKey1Incrementer")
	private DataFieldMaxValueIncrementer dataFieldMaxValueIncrementer;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberServiceImpl.class);

	@Override
	@Transactional(value = "alibabaTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void saveMemberInfo(Member member, List<EduExp> eduExps, List<WorkExp> workExps, List<ImpressLabel> impressLabels) {
		member.setId(dataFieldMaxValueIncrementer.nextLongValue());
		Integer shardId = ((int) member.getAlibabaId().charAt(0)) % 25;
		memberDAO.createMemberTable(shardId);			// 建表
		memberFanDAO.createMemberFanTable(shardId, member.getAlibabaId());
		memberFollowDAO.createMemberFollowTable(shardId, member.getAlibabaId());
		
		if (memberDAO.getIdByAlibabaId(shardId, member.getAlibabaId()) != null) {
			return;
		}
		MemberShard memberShard = new MemberShard();
		memberShard.setCreateTime(new Date());
		memberShard.setMemberId(member.getAlibabaId());
		memberShard.setShardId(shardId);
		memberShardDAO.saveMemberShard(memberShard);	// 保存标识
		member.setCreateTime(new Date());
		member.setUpdateTime(new Date());
		memberDAO.saveMember(shardId, member);			// 存会员
		
		for (EduExp eduExp : eduExps) {
			try {
				eduExp.setCreateTime(new Date());
				eduExpDAO.saveEduExp(eduExp);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}

		}
		
		for (WorkExp workExp : workExps) {
			try {
				workExp.setCreateTime(new Date());
				workExpDAO.saveWorkExp(workExp);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		
		for (ImpressLabel impressLabel : impressLabels) {
			try {
				Long labelId = impressLabelDAO.getIdByLabelName(impressLabel.getLabelName());
				if (labelId == null) {
					impressLabel.setCreateTime(new Date());
					labelId = impressLabelDAO.saveImpressLabel(impressLabel);
				}
				MemberImpressLabel memberImpressLabel = new MemberImpressLabel();
				memberImpressLabel.setCreateTime(new Date());
				memberImpressLabel.setMemberId(member.getAlibabaId());
				memberImpressLabel.setImpressLabelId(labelId);
				memberImpressLabelDAO.saveMemberImpressLabel(memberImpressLabel);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

}
