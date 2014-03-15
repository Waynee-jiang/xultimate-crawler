package alibaba.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import alibaba.biz.MemberFollowBiz;
import alibaba.po.MemberFollow;
import alibaba.service.MemberFollowService;

@Service("memberFollowServiceImpl")
public class MemberFollowServiceImpl implements MemberFollowService {

	@Resource(name = "memberFollowBizImpl")
	private MemberFollowBiz memberFollowBiz;
	
	@Override
	public void saveMemberFollow(MemberFollow memberFollow) {
		memberFollowBiz.saveMemberFollow(memberFollow);
	}

}
