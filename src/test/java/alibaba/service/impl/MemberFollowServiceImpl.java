package alibaba.service.impl;

import javax.annotation.Resource;

import alibaba.biz.MemberFollowBiz;
import alibaba.po.MemberFollow;
import alibaba.service.MemberFollowService;

public class MemberFollowServiceImpl implements MemberFollowService {

	@Resource(name = "alibabaMemberFollowBizImpl")
	private MemberFollowBiz memberFollowBiz;
	
	@Override
	public void saveMemberFollow(MemberFollow memberFollow) {
		memberFollowBiz.saveMemberFollow(memberFollow);
	}

}
