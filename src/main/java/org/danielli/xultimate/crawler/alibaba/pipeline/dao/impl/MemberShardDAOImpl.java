package org.danielli.xultimate.crawler.alibaba.pipeline.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.danielli.xultimate.crawler.alibaba.model.MemberShard;
import org.danielli.xultimate.crawler.alibaba.pipeline.dao.MemberShardDAO;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository("alibabaMemberShardDAO")
public class MemberShardDAOImpl extends BaseDAOImpl implements MemberShardDAO {

	@Override
	public void saveMemberShard(final MemberShard memberShard) {
		Assert.notNull(memberShard);
		final String sql = "INSERT INTO ali_member_shard (member_id, shard_id, create_time) VALUES (?, ?, ?)";
		this.getAlibabaJdbcTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql);
	            ps.setObject(1, memberShard.getMemberId());
	            ps.setObject(2, memberShard.getShardId());
	            ps.setObject(3, memberShard.getCreateTime());
	            return ps;
			}
			
		});
	}

	@Override
	public Integer getShardIdByMemberId(String memberId) {
		Assert.notNull(memberId);
		final String sql = "SELECT shard_id FROM ali_member_shard WHERE member_id = ?";
		List<Integer> result = this.getAlibabaJdbcTemplate().queryForList(sql, Integer.class, memberId);
		if (result.size() == 0) {
			return null;
		}
		return result.get(0);
	}

}
