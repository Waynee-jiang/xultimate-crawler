package org.danielli.xultimate.crawler.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.danielli.xultimate.context.format.Formatter;
import org.danielli.xultimate.context.kvStore.redis.RedisException;
import org.danielli.xultimate.context.kvStore.redis.jedis.ShardedJedisCallback;
import org.danielli.xultimate.context.kvStore.redis.jedis.ShardedJedisReturnedCallback;
import org.danielli.xultimate.context.kvStore.redis.jedis.support.ShardedJedisTemplate;
import org.danielli.xultimate.core.json.JSONTemplate;
import org.danielli.xultimate.core.json.ValueType;
import org.danielli.xultimate.crawler.LinkbaseHandler;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.crypto.DigestUtils;
import org.danielli.xultimate.util.crypto.MessageDigestAlgorithms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;

/**
 * 链接库处理器默认实现类。
 * 
 * @author Daniel Li
 * @since 12 Jun 2013
 */
@Service("linkbaseHandlerSupport")
public class LinkbaseHandlerSupport implements LinkbaseHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger("linkbase");
	
	public static final String MAIN_QUEUE = "mainQueue";
	
	public static final String BACKUP_QUEUE = "backupQueue";
	
	public static final String COMPLETION_SET = "completionSet";
	
	public static final String UNABLE_SET = "unableSet";
	
	public static final String TABLE_NAME_PATTERN = "crawler:${#linkId}";
	
	public static final String LINK_CREATE_TIME = "createTime";
	
	public static final String LINK_COLUMN_NAME = "link";
	
	public static final String PARENT_LINK_COLUMN_NAME = "plink";
	
	@Resource(name = "shardedJedisTemplate")
	private ShardedJedisTemplate shardedJedisTemplate;

	@Resource(name = "fastJSONTemplate")
	private JSONTemplate jsonTemplate;
	
	@Resource(name = "spelFormatter")
	private Formatter<String, Map<String, Object>, String> formatter;
	
	@Resource(name = "threadPoolTaskExecutor")
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	@Override
	public String getLinkId() {
		try {
			return shardedJedisTemplate.execute(new ShardedJedisReturnedCallback<String>() {
				@Override
				public String doInShardedJedis(ShardedJedis shardedJedis) {
					String linkId = shardedJedis.getShard(MAIN_QUEUE).rpoplpush(MAIN_QUEUE, BACKUP_QUEUE);
					if (StringUtils.isBlank(linkId))  
						return null;
					return linkId;
				}
			});
		} catch (RedisException e) {
			LOGGER.error("从队列中获取链接ID失败: " + e.getMessage(), e);
			return null;
		}
	}

	@Override
	public String getLinkUrlByLinkId(final String linkId) {
		try {
			return shardedJedisTemplate.execute(new ShardedJedisReturnedCallback<String>() {
				@Override
				public String doInShardedJedis(ShardedJedis shardedJedis) {
					Map<String, Object> parameter = new HashMap<>();
					parameter.put("linkId", linkId);
					String key = formatter.format(TABLE_NAME_PATTERN, parameter);
					Map<String, Object> urlMap = jsonTemplate.readValue(shardedJedis.get(key), new ValueType<HashMap<String, Object>>() { });
					return (String) urlMap.get(LINK_COLUMN_NAME);
				}
			});
		} catch (RedisException e) {
			LOGGER.error("获取" + linkId + "的链接URL失败: " + e.getMessage(), e);
			return null;
		}
	}

	@Override
	public void completionLinkId(final String linkId) {
		threadPoolTaskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					shardedJedisTemplate.execute(new ShardedJedisCallback() {
						
						@Override
						public void doInShardedJedis(ShardedJedis shardedJedis) {
							ShardedJedisPipeline jedisPipeline = shardedJedis.pipelined();
							jedisPipeline.lrem(BACKUP_QUEUE, 1, linkId);
							jedisPipeline.zadd(COMPLETION_SET, System.currentTimeMillis(), linkId);
							jedisPipeline.zrem(UNABLE_SET, linkId);
							jedisPipeline.sync();
						}
					});
				} catch (RedisException e) {
					LOGGER.error("添加" + linkId + "到完成集失败: " + e.getMessage(), e);
				}
			}
		});
	}
	
	@Override
	public void unableLinkId(final String linkId) {
		threadPoolTaskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					shardedJedisTemplate.execute(new ShardedJedisCallback() {
						
						@Override
						public void doInShardedJedis(ShardedJedis shardedJedis) {
							ShardedJedisPipeline jedisPipeline = shardedJedis.pipelined();
							jedisPipeline.lrem(BACKUP_QUEUE, 1, linkId);
							jedisPipeline.zincrby(UNABLE_SET, 1, linkId);
							jedisPipeline.sync();
						}
					});
				} catch (RedisException e) {
					LOGGER.error("添加" + linkId + "到不可用集失败: " + e.getMessage(), e);
				}
			}
		});
	}

	@Override
	public void addLinkUrls(final List<Map<String, Object>> linkUrlList) {
		threadPoolTaskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					shardedJedisTemplate.execute(new ShardedJedisCallback() {

						@Override
						public void doInShardedJedis(ShardedJedis shardedJedis) {
							for (Map<String, Object> linkMap : linkUrlList) {
								String linkUrl = (String) linkMap.get(LINK_COLUMN_NAME);
								try {
									String linkId = DigestUtils.digest(MessageDigestAlgorithms.MD5, linkUrl);
									Map<String, Object> parameter = new HashMap<>();
									parameter.put("linkId", linkId);
									String key = formatter.format(TABLE_NAME_PATTERN, parameter);
									if (shardedJedis.exists(key)) {
										continue;
									}
									ShardedJedisPipeline jedisPipeline = shardedJedis.pipelined();
									jedisPipeline.set(key, jsonTemplate.writeValueAsString(linkMap));
									jedisPipeline.lpush(MAIN_QUEUE, linkId);
									jedisPipeline.sync();
									LOGGER.info("添加" + linkUrl + "成功");
								} catch (Exception e) {
									LOGGER.error("添加" + linkUrl + "失败: " + e.getMessage(), e);
								}
							}
						}
					});
				} catch (Exception e) {
					LOGGER.error("未知错误" + e.getMessage(), e);
				}		
			}
		});
	}
}
