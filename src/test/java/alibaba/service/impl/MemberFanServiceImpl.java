package alibaba.service.impl;

import javax.annotation.Resource;

import alibaba.biz.MemberFanBiz;
import alibaba.po.MemberFan;
import alibaba.service.MemberFanService;

public class MemberFanServiceImpl implements MemberFanService {

	@Resource(name = "alibabaMemberFanBizImpl")
	private MemberFanBiz memberFanBiz;
	
	@Override
	public void saveMemberFan(MemberFan memberFan) {
		memberFanBiz.saveMemberFan(memberFan);
	}

}
