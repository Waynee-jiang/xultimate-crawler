package alibaba.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import alibaba.biz.EduExpBiz;
import alibaba.biz.ImpressLabelBiz;
import alibaba.biz.MemberBiz;
import alibaba.biz.MemberImpressLabelBiz;
import alibaba.biz.WorkExpBiz;
import alibaba.po.EduExp;
import alibaba.po.ImpressLabel;
import alibaba.po.Member;
import alibaba.po.MemberImpressLabel;
import alibaba.po.WorkExp;
import alibaba.service.MemberService;

@Service("memberServiceImpl")
public class MemberServiceImpl implements MemberService {

	@Resource(name = "memberBizImpl")
	private MemberBiz memberBiz;
	
	@Resource(name = "workExpBizImpl")
	private WorkExpBiz workExpBiz;
	
	@Resource(name = "eduExpBizImpl")
	private EduExpBiz eduExpBiz;
	
	@Resource(name = "impressLabelBizImpl")
	private ImpressLabelBiz impressLabelBiz;
	
	@Resource(name = "memberImpressLabelBizImpl")
	private MemberImpressLabelBiz memberImpressLabelBiz;

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	@Override
	public Long getOrSetMemberIdByAlibabaId(String alibabaId) {
		return memberBiz.getOrSetMemberIdByAlibabaId(alibabaId);
	}
	
	@Override
	public void saveMemberInfo(Member member, List<EduExp> eduExps, List<WorkExp> workExps, List<ImpressLabel> impressLabels) {
		memberBiz.saveMember(member);
		
		for (EduExp eduExp : eduExps) {
			try {
				eduExp.setMemberId(member.getId());
				eduExpBiz.saveEduExp(eduExp);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		
		for (WorkExp workExp : workExps) {
			try {
				workExp.setMemberId(member.getId());
				workExpBiz.saveWorkExp(workExp);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		
		for (ImpressLabel impressLabel : impressLabels) {
			try {
				Long labelId = impressLabelBiz.getIdByLabelName(impressLabel.getLabelName());
				if (labelId == null) {
					impressLabelBiz.saveImpressLabel(impressLabel);
					labelId = impressLabel.getId();
				}
				MemberImpressLabel memberImpressLabel = new MemberImpressLabel();
				memberImpressLabel.setMemberId(member.getId());
				memberImpressLabel.setImpressLabelId(labelId);
				memberImpressLabelBiz.saveMemberImpressLabel(memberImpressLabel);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
	
}
