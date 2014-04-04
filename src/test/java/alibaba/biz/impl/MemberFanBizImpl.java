package alibaba.biz.impl;

import javax.annotation.Resource;

import org.danielli.xultimate.jdbc.datasource.lookup.DataSourceContext;
import org.danielli.xultimate.shard.ShardInfoGenerator;
import org.danielli.xultimate.shard.dto.ShardInfo;
import org.springframework.stereotype.Service;

import alibaba.biz.MemberFanBiz;
import alibaba.dao.MemberFanDAO;
import alibaba.po.MemberFan;

@Service("memberFanBizImpl")
public class MemberFanBizImpl implements MemberFanBiz {

	@Resource(name = "myBatisShardInfoGenerator")
	private ShardInfoGenerator shardInfoGenerator;
	
	@Resource(name = "memberFanDAO")
	private MemberFanDAO memberFanDAO;
	
	@Override
	public void saveMemberFan(MemberFan memberFan) {
		ShardInfo shardInfo = shardInfoGenerator.createShardInfo("crawler_db", "MEMBER_RELATION", memberFan.getMemberId());
		DataSourceContext.setCurrentLookupKey(shardInfo.getVirtualRoutingDataSourceKey("crawler_db"));
		memberFanDAO.saveMemberFan(shardInfo.getPartitionedTableShardId(), memberFan);
	}

}
