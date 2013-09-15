package org.danielli.xultimate.crawler.pipeline;

import java.util.List;

import org.danielli.xultimate.crawler.model.EduExp;
import org.danielli.xultimate.crawler.model.ImpressLabel;
import org.danielli.xultimate.crawler.model.Member;
import org.danielli.xultimate.crawler.model.MemberFan;
import org.danielli.xultimate.crawler.model.MemberFollow;
import org.danielli.xultimate.crawler.model.WorkExp;
import org.danielli.xultimate.crawler.pipeline.service.MemberFanService;
import org.danielli.xultimate.crawler.pipeline.service.MemberFollowService;
import org.danielli.xultimate.crawler.pipeline.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PipelineService {
	
	@Autowired
	private MemberFanService memberFanService;
	
	@Autowired
	private MemberFollowService memberFollowService;
	
	@Autowired
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
