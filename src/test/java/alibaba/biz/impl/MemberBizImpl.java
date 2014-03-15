package alibaba.biz.impl;

import httl.util.StringUtils;

import javax.annotation.Resource;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.lang3.math.NumberUtils;
import org.danielli.xultimate.context.format.FormatterUtils;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.MemcachedClientMutex;
import org.danielli.xultimate.context.kvStore.redis.jedis.ShardedJedisReturnedCallback;
import org.danielli.xultimate.context.kvStore.redis.jedis.support.ShardedJedisTemplate;
import org.danielli.xultimate.jdbc.datasource.lookup.RoutingDataSourceUtils;
import org.danielli.xultimate.shard.ShardInfoGenerator;
import org.danielli.xultimate.shard.dto.ShardInfo;
import org.danielli.xultimate.util.BooleanUtils;
import org.joda.time.DateTime;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import alibaba.biz.MemberBiz;
import alibaba.dao.MemberDAO;
import alibaba.po.Member;

@Service("memberBizImpl")
public class MemberBizImpl implements MemberBiz {

	@Resource(name = "alibabaPrimaryKey1Incrementer")
	private DataFieldMaxValueIncrementer dataFieldMaxValueIncrementer;
	
	@Resource(name = "alibabaShardedJedisTemplate")
	private ShardedJedisTemplate shardedJedisTemplate;
	
	@Resource(name = "alibabaMembercachedClient")
	private MemcachedClient memcachedClient;
	
	@Resource(name = "alibabaMembercachedClientMutex")
	private MemcachedClientMutex memcachedClientMutex;
	
	@Resource(name = "memberDAO")
	private MemberDAO memberDAO;
	
	@Resource(name = "shardInfoGenerator")
	private ShardInfoGenerator shardInfoGenerator;
	
	@Override
	public void saveMember(final Member member) {
		Boolean isNewId = shardedJedisTemplate.execute(new ShardedJedisReturnedCallback<Boolean>() {
			@Override
			public Boolean doInShardedJedis(ShardedJedis shardedJedis) throws Exception {
				String memberIdKey = FormatterUtils.format("Member:alibabaId:{1}", member.getAlibabaId());
				String memberId = shardedJedis.get(memberIdKey);
				if (StringUtils.isEmpty(memberId)) {
					String memberIdKeyLock = FormatterUtils.format("{1}.lock", memberIdKey);
					if (memcachedClientMutex.tryLock(memcachedClient, memberIdKeyLock)) {
						try {
							Long tmpMemberId = dataFieldMaxValueIncrementer.nextLongValue();
							shardedJedis.set(memberIdKey, String.valueOf(tmpMemberId));
							member.setId(member.getId());
							return true;
						} finally {
							memcachedClientMutex.unlock(memcachedClient, memberIdKeyLock);
						}
					} else {
						Thread.sleep(500);
						return doInShardedJedis(shardedJedis);
					}
				} else {
					member.setId(NumberUtils.createLong(memberId));
					String hasSaveKey = FormatterUtils.format("{1}.save", memberIdKey);
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
			ShardInfo shardInfo = shardInfoGenerator.createShardInfo("alibaba_crawler", "MEMBER", member.getId());
			RoutingDataSourceUtils.setRoutingDataSourceKey(shardInfo.getVirtualRoutingDataSourceKey("alibaba_crawler"));
			memberDAO.saveMember(shardInfo.getPartitionedTableShardId(), member);
		}
	}
	
	@Override
	public Long getOrSetMemberIdByAlibabaId(final String alibabaId) {
		return shardedJedisTemplate.execute(new ShardedJedisReturnedCallback<Long>() {
			@Override
			public Long doInShardedJedis(ShardedJedis shardedJedis) throws Exception {
				String memberIdKey = FormatterUtils.format("Member:alibabaId:{1}", alibabaId);
				String memberId = shardedJedis.get(memberIdKey);
				if (StringUtils.isEmpty(memberId)) {
					String memberIdKeyLock = FormatterUtils.format("{1}.lock", memberIdKey);
					if (memcachedClientMutex.tryLock(memcachedClient, memberIdKeyLock)) {
						try {
							Long tmpMemberId = dataFieldMaxValueIncrementer.nextLongValue();
							shardedJedis.set(memberIdKey, String.valueOf(memberId));
							shardedJedis.set(FormatterUtils.format("{1}.save", memberIdKey), "false");
							return tmpMemberId;
						} finally {
							memcachedClientMutex.unlock(memcachedClient, memberIdKeyLock);
						}
					} else {
						Thread.sleep(500);
						return doInShardedJedis(shardedJedis);
					}
				}
				return NumberUtils.createLong(memberId);
			}
		});
	}
}
