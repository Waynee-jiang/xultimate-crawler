package org.danielli.xultimate.crawler.tools;

import java.util.Set;

import org.danielli.xultimate.context.kvStore.redis.RedisException;
import org.danielli.xultimate.context.kvStore.redis.jedis.ShardedJedisCallback;
import org.danielli.xultimate.context.kvStore.redis.jedis.support.ShardedJedisTemplate;
import org.danielli.xultimate.crawler.support.LinkbaseHandlerSupport;
import org.danielli.xultimate.util.ArrayUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.ShardedJedis;

/**
 * 数据倒换工具。将不可用Set中的链接重新加入到欲执行Queue。
 * 
 * @author Daniel Li
 * @since 12 Jun 2013
 */
public class UnableHandler {
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:applicationContext-service-crawler*.xml");
		ShardedJedisTemplate shardedJedisTemplate = applicationContext.getBean("shardedJedisTemplate", ShardedJedisTemplate.class);
		try {
			shardedJedisTemplate.execute(new ShardedJedisCallback<Void>() {
				
				@Override
				public Void doInShardedJedis(ShardedJedis shardedJedis) {
					Set<String> unalbeSet = shardedJedis.zrangeByScore(LinkbaseHandlerSupport.UNABLE_SET, 1, 2);
					try {
						shardedJedis.lpush(LinkbaseHandlerSupport.MAIN_QUEUE, unalbeSet.toArray(ArrayUtils.newInstance(String.class, unalbeSet.size())));
						System.out.println("添加到队列成功");
					} catch (Exception e) {
						System.out.println("添加到队列失败: " + e.getMessage());
						e.printStackTrace();
					} 
					return null;
				}
			});
		} catch (RedisException e) {
			e.printStackTrace();
		} finally {
			applicationContext.close();
		}
	}
}
