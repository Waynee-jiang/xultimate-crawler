package alibaba.biz.impl;

import httl.util.StringUtils;

import javax.annotation.Resource;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.lang3.math.NumberUtils;
import org.danielli.xultimate.context.format.FormatterUtils;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.MemcachedLockFactory;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.MemcachedLockFactory.MemcachedLock;
import org.danielli.xultimate.context.kvStore.redis.jedis.ShardedJedisCallback;
import org.danielli.xultimate.context.kvStore.redis.jedis.support.ShardedJedisTemplate;
import org.danielli.xultimate.jdbc.datasource.lookup.DataSourceContext;
import org.danielli.xultimate.shard.ShardInfoGenerator;
import org.danielli.xultimate.shard.dto.ShardInfo;
import org.joda.time.DateTime;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import alibaba.biz.ImpressLabelBiz;
import alibaba.dao.ImpressLabelDAO;
import alibaba.po.ImpressLabel;

@Service("impressLabelBizImpl")
public class ImpressLabelBizImpl implements ImpressLabelBiz, InitializingBean {

	@Resource(name = "alibabaShardedJedisTemplate")
	private ShardedJedisTemplate shardedJedisTemplate;
	
	@Resource(name = "memcachedClient")
	private MemcachedClient memcachedClient;
	
	@Resource(name = "memcachedLockFactory")
	private MemcachedLockFactory memcachedLockFactory;
	
	@Resource(name = "impressLabelIncrementer")
	private DataFieldMaxValueIncrementer dataFieldMaxValueIncrementer;
	
	@Resource(name = "impressLabelDAO")
	private ImpressLabelDAO impressLabelDAO;
	
	@Resource(name = "myBatisShardInfoGenerator")
	private ShardInfoGenerator shardInfoGenerator;
	
	private MemcachedLock memcachedLock;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		memcachedLock = memcachedLockFactory.getLock(10);
	}
	
	@Override
	public Long getIdByLabelName(final String labelName) {
		return shardedJedisTemplate.execute(new ShardedJedisCallback<Long>() {
			@Override
			public Long doInShardedJedis(ShardedJedis shardedJedis) throws Exception {
				String labelIdKey = FormatterUtils.format("ImpressLabel:labelName:{0}", labelName);
				String labelId = shardedJedis.get(labelIdKey);
				return NumberUtils.createLong(labelId);
			}
		});
	}
	
	@Override
	public void saveImpressLabel(final ImpressLabel impressLabel) {
		Boolean isNewId = shardedJedisTemplate.execute(new ShardedJedisCallback<Boolean>() {
			@Override
			public Boolean doInShardedJedis(ShardedJedis shardedJedis) throws Exception {
				String labelIdKey = FormatterUtils.format("ImpressLabel:labelName:{0}", impressLabel.getLabelName());
				String labelId = shardedJedis.get(labelIdKey);
				if (StringUtils.isEmpty(labelId)) {
					String labelIdKeyLock = FormatterUtils.format("{0}.lock", labelIdKey);
					if (memcachedLock.tryLock(labelIdKeyLock)) {
						try {
							Long tmpLabelId = dataFieldMaxValueIncrementer.nextLongValue();
							shardedJedis.set(labelIdKey, String.valueOf(tmpLabelId));
							impressLabel.setId(tmpLabelId);
							return true;
						} finally {
							memcachedLock.unlock(labelIdKeyLock);
						}
					} else {
						Thread.sleep(500);
						return doInShardedJedis(shardedJedis);
					}
				}
				impressLabel.setId(NumberUtils.createLong(labelId));
				return false;
			}
		});
		if (isNewId) {
			if (impressLabel.getCreateTime() == null) {
				impressLabel.setCreateTime(new DateTime().toDate());
			}
			ShardInfo shardInfo = shardInfoGenerator.createShardInfo("crawler_db", "IMPRESS_LABEL", impressLabel.getId());
			DataSourceContext.setCurrentLookupKey(shardInfo.getVirtualRoutingDataSourceKey("crawler_db"));
			impressLabelDAO.saveImpressLabel(shardInfo.getPartitionedTableShardId(), impressLabel);
		}
	}



}
