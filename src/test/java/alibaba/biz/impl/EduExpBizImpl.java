package alibaba.biz.impl;

import javax.annotation.Resource;

import org.danielli.xultimate.jdbc.datasource.lookup.DataSourceContext;
import org.danielli.xultimate.shard.ShardInfoGenerator;
import org.danielli.xultimate.shard.dto.ShardInfo;
import org.joda.time.DateTime;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.stereotype.Service;

import alibaba.biz.EduExpBiz;
import alibaba.dao.EduExpDAO;
import alibaba.po.EduExp;

@Service("eduExpBizImpl")
public class EduExpBizImpl implements EduExpBiz {

	@Resource(name = "eduExpIncrementer")
	private DataFieldMaxValueIncrementer dataFieldMaxValueIncrementer;
	
	@Resource(name = "eduExpDAO")
	private EduExpDAO eduExpDAO;
	
	@Resource(name = "myBatisShardInfoGenerator")
	private ShardInfoGenerator shardInfoGenerator;
	
	@Override
	public void saveEduExp(EduExp eduExp) {
		eduExp.setId(dataFieldMaxValueIncrementer.nextLongValue());
		if (eduExp.getCreateTime() == null) {
			DateTime currentTime = new DateTime();
			eduExp.setCreateTime(currentTime.toDate());
		}
		ShardInfo shardInfo = shardInfoGenerator.createShardInfo("crawler_db", "MEMBER", eduExp.getMemberId());
		DataSourceContext.setCurrentLookupKey(shardInfo.getVirtualRoutingDataSourceKey("crawler_db"));
		eduExpDAO.saveEduExp(shardInfo.getPartitionedTableShardId(), eduExp);
	}

}
