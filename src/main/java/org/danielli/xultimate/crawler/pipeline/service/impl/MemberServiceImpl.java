package org.danielli.xultimate.crawler.pipeline.service.impl;

import java.util.Date;
import java.util.List;

import org.danielli.xultimate.crawler.model.EduExp;
import org.danielli.xultimate.crawler.model.ImpressLabel;
import org.danielli.xultimate.crawler.model.Member;
import org.danielli.xultimate.crawler.model.MemberImpressLabel;
import org.danielli.xultimate.crawler.model.MemberShard;
import org.danielli.xultimate.crawler.model.WorkExp;
import org.danielli.xultimate.crawler.pipeline.dao.EduExpDAO;
import org.danielli.xultimate.crawler.pipeline.dao.ImpressLabelDAO;
import org.danielli.xultimate.crawler.pipeline.dao.MemberDAO;
import org.danielli.xultimate.crawler.pipeline.dao.MemberFanDAO;
import org.danielli.xultimate.crawler.pipeline.dao.MemberFollowDAO;
import org.danielli.xultimate.crawler.pipeline.dao.MemberImpressLabelDAO;
import org.danielli.xultimate.crawler.pipeline.dao.MemberShardDAO;
import org.danielli.xultimate.crawler.pipeline.dao.WorkExpDAO;
import org.danielli.xultimate.crawler.pipeline.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private EduExpDAO eduExpDAO;
	
	@Autowired
	private WorkExpDAO workExpDAO;
	
	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private MemberShardDAO memberShardDAO;

	@Autowired
	private ImpressLabelDAO impressLabelDAO;

	@Autowired
	private MemberImpressLabelDAO memberImpressLabelDAO;
	
	@Autowired
	private MemberFanDAO memberFanDAO;
	
	@Autowired
	private MemberFollowDAO memberFollowDAO;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberServiceImpl.class);

	@Override
	public void saveMemberInfo(Member member, List<EduExp> eduExps, List<WorkExp> workExps, List<ImpressLabel> impressLabels) {
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
