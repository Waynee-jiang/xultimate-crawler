package alibaba.service;

import java.util.List;

import alibaba.po.EduExp;
import alibaba.po.ImpressLabel;
import alibaba.po.Member;
import alibaba.po.WorkExp;

public interface MemberService {
	
	Long getOrSetMemberIdByAlibabaId(String alibabaId);
	
	/***
	 * 保存
	 */
	void saveMemberInfo(Member member, List<EduExp> eduExps, List<WorkExp> workExps, List<ImpressLabel> impressLabels);
}
