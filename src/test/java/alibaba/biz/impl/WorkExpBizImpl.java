package alibaba.biz.impl;

import javax.annotation.Resource;

import org.danielli.xultimate.jdbc.datasource.lookup.RoutingDataSourceUtils;
import org.danielli.xultimate.shard.ShardInfoGenerator;
import org.danielli.xultimate.shard.dto.ShardInfo;
import org.joda.time.DateTime;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.stereotype.Service;

import alibaba.biz.WorkExpBiz;
import alibaba.dao.WorkExpDAO;
import alibaba.po.WorkExp;

@Service("workExpBizImpl")
public class WorkExpBizImpl implements WorkExpBiz {

	@Resource(name = "workExpIncrementer")
	private DataFieldMaxValueIncrementer dataFieldMaxValueIncrementer;
	
	@Resource(name = "workExpDAO")
	private WorkExpDAO workExpDAO;
	
	@Resource(name = "myBatisShardInfoGenerator")
	private ShardInfoGenerator shardInfoGenerator;
	
	@Override
	public void saveWorkExp(WorkExp workExp) {
		workExp.setId(dataFieldMaxValueIncrementer.nextLongValue());
		if (workExp.getCreateTime() == null) {
			DateTime currentTime = new DateTime();
			workExp.setCreateTime(currentTime.toDate());
		}
		ShardInfo shardInfo = shardInfoGenerator.createShardInfo("crawler_db", "MEMBER", workExp.getMemberId());
		RoutingDataSourceUtils.setRoutingDataSourceKey(shardInfo.getVirtualRoutingDataSourceKey("crawler_db"));
		workExpDAO.saveWorkExp(shardInfo.getPartitionedTableShardId(), workExp);
	}

}
