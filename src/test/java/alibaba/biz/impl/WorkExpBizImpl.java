package alibaba.biz.impl;

import javax.annotation.Resource;

import org.danielli.xultimate.jdbc.datasource.lookup.RoutingDataSourceUtils;
import org.danielli.xultimate.shard.ShardInfoGenerator;
import org.danielli.xultimate.shard.dto.ShardInfo;
import org.joda.time.DateTime;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;

import alibaba.biz.WorkExpBiz;
import alibaba.dao.WorkExpDAO;
import alibaba.po.WorkExp;

public class WorkExpBizImpl implements WorkExpBiz {

	@Resource(name = "asdfasdf")
	private DataFieldMaxValueIncrementer dataFieldMaxValueIncrementer;
	
	@Resource(name = "workExpDAO")
	private WorkExpDAO workExpDAO;
	
	@Resource(name = "shardInfoGenerator")
	private ShardInfoGenerator shardInfoGenerator;
	
	@Override
	public void saveWorkExp(WorkExp workExp) {
		workExp.setId(dataFieldMaxValueIncrementer.nextLongValue());
		if (workExp.getCreateTime() == null) {
			DateTime currentTime = new DateTime();
			workExp.setCreateTime(currentTime.toDate());
		}
		ShardInfo shardInfo = shardInfoGenerator.createShardInfo("alibaba_crawler", "MEMBER", workExp.getMemberId());
		RoutingDataSourceUtils.setRoutingDataSourceKey(shardInfo.getVirtualRoutingDataSourceKey("alibaba_crawler"));
		workExpDAO.saveWorkExp(shardInfo.getPartitionedTableShardId(), workExp);
	}

}
