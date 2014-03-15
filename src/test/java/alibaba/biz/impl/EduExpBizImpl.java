package alibaba.biz.impl;

import javax.annotation.Resource;

import org.danielli.xultimate.jdbc.datasource.lookup.RoutingDataSourceUtils;
import org.danielli.xultimate.shard.ShardInfoGenerator;
import org.danielli.xultimate.shard.dto.ShardInfo;
import org.joda.time.DateTime;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;

import alibaba.biz.EduExpBiz;
import alibaba.dao.EduExpDAO;
import alibaba.po.EduExp;

public class EduExpBizImpl implements EduExpBiz {

	@Resource(name = "asdfasdf")
	private DataFieldMaxValueIncrementer dataFieldMaxValueIncrementer;
	
	@Resource(name = "eduExpDAO")
	private EduExpDAO eduExpDAO;
	
	@Resource(name = "shardInfoGenerator")
	private ShardInfoGenerator shardInfoGenerator;
	
	@Override
	public void saveEduExp(EduExp eduExp) {
		eduExp.setId(dataFieldMaxValueIncrementer.nextLongValue());
		if (eduExp.getCreateTime() == null) {
			DateTime currentTime = new DateTime();
			eduExp.setCreateTime(currentTime.toDate());
		}
		ShardInfo shardInfo = shardInfoGenerator.createShardInfo("alibaba_crawler", "MEMBER", eduExp.getMemberId());
		RoutingDataSourceUtils.setRoutingDataSourceKey(shardInfo.getVirtualRoutingDataSourceKey("alibaba_crawler"));
		eduExpDAO.saveEduExp(shardInfo.getPartitionedTableShardId(), eduExp);
	}

}
