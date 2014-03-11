package org.danielli.xultimate.crawler.alibaba.pipeline;

import java.util.List;

import javax.annotation.Resource;

import org.danielli.xultimate.crawler.alibaba.model.EduExp;
import org.danielli.xultimate.crawler.alibaba.model.ImpressLabel;
import org.danielli.xultimate.crawler.alibaba.model.Member;
import org.danielli.xultimate.crawler.alibaba.model.MemberFan;
import org.danielli.xultimate.crawler.alibaba.model.MemberFollow;
import org.danielli.xultimate.crawler.alibaba.model.WorkExp;
import org.danielli.xultimate.crawler.alibaba.pipeline.service.MemberFanService;
import org.danielli.xultimate.crawler.alibaba.pipeline.service.MemberFollowService;
import org.danielli.xultimate.crawler.alibaba.pipeline.service.MemberService;
import org.springframework.stereotype.Service;

@Service("alibabaPipelineService")
public class PipelineService {
	
	@Resource(name = "alibabaMemberFanService")
	private MemberFanService memberFanService;
	
	@Resource(name = "alibabaMemberFollowService")
	private MemberFollowService memberFollowService;
	
	@Resource(name = "alibabaMemberService")
	private MemberService memberService;
	
	/***
	 * 保存
	 */
	public void saveMemberInfo(Member member, List<EduExp> eduExps, List<WorkExp> workExps, List<ImpressLabel> impressLabels) {
		memberService.saveMemberInfo(member, eduExps, workExps, impressLabels);
	}
	
	/**
	 * 保存
	 */
	public void saveMemberFan(MemberFan memberFan) {
		memberFanService.saveMemberFan(memberFan);
	}
	
	/**
	 * 保存
	 */
	public void saveMemberFollow(MemberFollow memberFollow) {
		memberFollowService.saveMemberFollow(memberFollow);
	}
}
