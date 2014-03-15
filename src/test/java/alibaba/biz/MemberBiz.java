package alibaba.biz;

import alibaba.po.Member;

public interface MemberBiz {
	
	Long getOrSetMemberIdByAlibabaId(String alibabaId);
	
	/***
	 * 保存
	 */
	void saveMember(Member member);
}
