package alibaba.biz.impl;

import javax.annotation.Resource;

import org.danielli.xultimate.jdbc.datasource.lookup.RoutingDataSourceUtils;
import org.danielli.xultimate.shard.ShardInfoGenerator;
import org.danielli.xultimate.shard.dto.ShardInfo;
import org.springframework.stereotype.Service;

import alibaba.biz.MemberFollowBiz;
import alibaba.dao.MemberFollowDAO;
import alibaba.po.MemberFollow;

@Service("memberFollowBizImpl")
public class MemberFollowBizImpl implements MemberFollowBiz {

	@Resource(name = "myBatisShardInfoGenerator")
	private ShardInfoGenerator shardInfoGenerator;
	
	@Resource(name = "memberFollowDAO")
	private MemberFollowDAO memberFollowDAO;
	
	@Override
	public void saveMemberFollow(MemberFollow memberFollow) {
		ShardInfo shardInfo = shardInfoGenerator.createShardInfo("crawler_db", "MEMBER_RELATION", memberFollow.getMemberId());
		RoutingDataSourceUtils.setRoutingDataSourceKey(shardInfo.getVirtualRoutingDataSourceKey("crawler_db"));
		memberFollowDAO.saveMemberFollow(shardInfo.getPartitionedTableShardId(), memberFollow);
	}

}
