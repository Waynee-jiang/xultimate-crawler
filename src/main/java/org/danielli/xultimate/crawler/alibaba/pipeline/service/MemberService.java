package org.danielli.xultimate.crawler.alibaba.pipeline.service;

import java.util.List;

import org.danielli.xultimate.crawler.alibaba.model.EduExp;
import org.danielli.xultimate.crawler.alibaba.model.ImpressLabel;
import org.danielli.xultimate.crawler.alibaba.model.Member;
import org.danielli.xultimate.crawler.alibaba.model.WorkExp;

public interface MemberService {
	
	/***
	 * 保存
	 */
	void saveMemberInfo(Member member, List<EduExp> eduExps, List<WorkExp> workExps, List<ImpressLabel> impressLabels);
}
