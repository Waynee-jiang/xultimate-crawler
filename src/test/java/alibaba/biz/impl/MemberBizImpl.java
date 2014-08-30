package alibaba.biz.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.math.NumberUtils;
import org.danielli.xultimate.context.format.FormatterUtils;
import org.danielli.xultimate.context.kvStore.memcached.MemcachedException;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.MemcachedLockFactory;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.MemcachedLockFactory.MemcachedLock;
import org.danielli.xultimate.context.kvStore.redis.jedis.ShardedJedisCallback;
import org.danielli.xultimate.context.kvStore.redis.jedis.support.ShardedJedisTemplate;
import org.danielli.xultimate.jdbc.datasource.lookup.DataSourceContext;
import org.danielli.xultimate.shard.ShardInfoGenerator;
import org.danielli.xultimate.shard.dto.ShardInfo;
import org.danielli.xultimate.util.BooleanUtils;
import org.danielli.xultimate.util.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import alibaba.biz.MemberBiz;
import alibaba.dao.MemberDAO;
import alibaba.po.Member;

@Service("memberBizImpl")
public class MemberBizImpl implements MemberBiz, InitializingBean {

	@Resource(name = "memberIncrementer")
	private DataFieldMaxValueIncrementer dataFieldMaxValueIncrementer;
	
	@Resource(name = "alibabaShardedJedisTemplate")
	private ShardedJedisTemplate shardedJedisTemplate;
	
	@Resource(name = "memcachedLockFactory")
	private MemcachedLockFactory memcachedLockFactory;
	
	@Resource(name = "memberDAO")
	private MemberDAO memberDAO;
	
	@Resource(name = "myBatisShardInfoGenerator")
	private ShardInfoGenerator shardInfoGenerator;
	
	private MemcachedLock memcachedLock;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		memcachedLock = memcachedLockFactory.getLock(10);
	}
	
	@Override
	public void saveMember(final Member member) {
		Boolean isNewId = shardedJedisTemplate.execute(new ShardedJedisCallback<Boolean>() {
			@Override
			public Boolean doInShardedJedis(ShardedJedis shardedJedis) throws Exception {
				String memberIdKey = FormatterUtils.format("Member:alibabaId:{0}", member.getAlibabaId());
				String memberId = shardedJedis.get(memberIdKey);
				if (StringUtils.isEmpty(memberId)) {
					String memberIdKeyLock = FormatterUtils.format("{0}.lock", memberIdKey);
					try {
						if (memcachedLock.tryLock(memberIdKeyLock)) {
							try {
								Long tmpMemberId = dataFieldMaxValueIncrementer.nextLongValue();
								shardedJedis.set(memberIdKey, String.valueOf(tmpMemberId));
								member.setId(tmpMemberId);
								return true;
							} finally {
								memcachedLock.unlock(memberIdKeyLock);
							}
						} else {
							Thread.sleep(500);
							return doInShardedJedis(shardedJedis);
						}
					} catch (MemcachedException e) {
						Long tmpMemberId = dataFieldMaxValueIncrementer.nextLongValue();
						shardedJedis.set(memberIdKey, String.valueOf(tmpMemberId));
						member.setId(tmpMemberId);
						return true;
					}
				} else {
					member.setId(NumberUtils.createLong(memberId));
					String hasSaveKey = FormatterUtils.format("{0}.save", memberIdKey);
					String hasSave = shardedJedis.get(hasSaveKey);
					if (BooleanUtils.isFalse((BooleanUtils.toBooleanObject(hasSave)))) {
						shardedJedis.del(hasSaveKey);
						return true;
					}
					return false;
				}
			}
		});
		if (isNewId) {
			if (member.getCreateTime() == null) {
				DateTime currentTime = new DateTime();
				member.setCreateTime(currentTime.toDate());
			}
			if (member.getUpdateTime() == null) {
				member.setUpdateTime(member.getCreateTime());
			}
			ShardInfo shardInfo = shardInfoGenerator.createShardInfo("crawler_db", "MEMBER", member.getId());
			DataSourceContext.setCurrentLookupKey(shardInfo.getVirtualRoutingDataSourceKey("crawler_db"));
			memberDAO.saveMember(shardInfo.getPartitionedTableShardId(), member);
		}
	}
	
	@Override
	public Long getOrSetMemberIdByAlibabaId(final String alibabaId) {
		return shardedJedisTemplate.execute(new ShardedJedisCallback<Long>() {
			@Override
			public Long doInShardedJedis(ShardedJedis shardedJedis) throws Exception {
				String memberIdKey = FormatterUtils.format("Member:alibabaId:{0}", alibabaId);
				String memberId = shardedJedis.get(memberIdKey);
				if (StringUtils.isEmpty(memberId)) {
					String memberIdKeyLock = FormatterUtils.format("{0}.lock", memberIdKey);
					try {
						if (memcachedLock.tryLock(memberIdKeyLock)) {
							try {
								Long tmpMemberId = dataFieldMaxValueIncrementer.nextLongValue();
								shardedJedis.set(memberIdKey, String.valueOf(tmpMemberId));
								shardedJedis.set(FormatterUtils.format("{0}.save", memberIdKey), "false");
								return tmpMemberId;
							} finally {
								memcachedLock.unlock(memberIdKeyLock);
							}
						} else {
							Thread.sleep(500);
							return doInShardedJedis(shardedJedis);
						}
					} catch (MemcachedException e) {
						Long tmpMemberId = dataFieldMaxValueIncrementer.nextLongValue();
						shardedJedis.set(memberIdKey, String.valueOf(tmpMemberId));
						shardedJedis.set(FormatterUtils.format("{0}.save", memberIdKey), "false");
						return tmpMemberId;
					}
				}
				return NumberUtils.createLong(memberId);
			}
		});
	}
}
