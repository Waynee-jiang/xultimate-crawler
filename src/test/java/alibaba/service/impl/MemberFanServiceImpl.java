package alibaba.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import alibaba.biz.MemberFanBiz;
import alibaba.po.MemberFan;
import alibaba.service.MemberFanService;

@Service("memberFanServiceImpl")
public class MemberFanServiceImpl implements MemberFanService {

	@Resource(name = "memberFanBizImpl")
	private MemberFanBiz memberFanBiz;
	
	@Override
	public void saveMemberFan(MemberFan memberFan) {
		memberFanBiz.saveMemberFan(memberFan);
	}

}
