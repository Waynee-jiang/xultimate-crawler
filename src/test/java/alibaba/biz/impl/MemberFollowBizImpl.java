package alibaba.biz.impl;

import javax.annotation.Resource;

import org.danielli.xultimate.jdbc.datasource.lookup.RoutingDataSourceUtils;
import org.danielli.xultimate.shard.ShardInfoGenerator;
import org.danielli.xultimate.shard.dto.ShardInfo;

import alibaba.biz.MemberFollowBiz;
import alibaba.dao.MemberFollowDAO;
import alibaba.po.MemberFollow;

public class MemberFollowBizImpl implements MemberFollowBiz {

	@Resource(name = "shardInfoGenerator")
	private ShardInfoGenerator shardInfoGenerator;
	
	@Resource(name = "memberFollowDAO")
	private MemberFollowDAO memberFollowDAO;
	
	@Override
	public void saveMemberFollow(MemberFollow memberFollow) {
		ShardInfo shardInfo = shardInfoGenerator.createShardInfo("alibaba_crawler", "IMPRESS_LABEL", memberFollow.getMemberId());
		RoutingDataSourceUtils.setRoutingDataSourceKey(shardInfo.getVirtualRoutingDataSourceKey("alibaba_crawler"));
		memberFollowDAO.saveMemberFollow(shardInfo.getPartitionedTableShardId(), memberFollow);
	}

}
