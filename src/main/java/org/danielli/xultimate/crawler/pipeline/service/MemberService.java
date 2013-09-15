package org.danielli.xultimate.crawler.pipeline.service;

import java.util.List;

import org.danielli.xultimate.crawler.model.EduExp;
import org.danielli.xultimate.crawler.model.ImpressLabel;
import org.danielli.xultimate.crawler.model.Member;
import org.danielli.xultimate.crawler.model.WorkExp;

public interface MemberService {
	
	/***
	 * 保存
	 */
	void saveMemberInfo(Member member, List<EduExp> eduExps, List<WorkExp> workExps, List<ImpressLabel> impressLabels);
}
