package org.danielli.xultimate.crawler.pipeline.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.danielli.xultimate.crawler.model.MemberFollow;
import org.danielli.xultimate.crawler.pipeline.dao.MemberFollowDAO;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class MemberFollowDAOImpl extends BaseDAOImpl implements MemberFollowDAO {

	private Integer getTableId(String memberId) {
		return ((int) memberId.charAt(3)) % 25;
	}
	
	@Override
	public Long saveMemberFollow(Integer shardId, final MemberFollow memberFollow) {
		Assert.notNull(shardId);
		Assert.notNull(memberFollow);
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("INSERT INTO ali_member_follow_").append(shardId).append("_").append(getTableId(memberFollow.getMemberId())).append(" ");
		sqlBuilder.append("(member_id, follow_member_id, create_time) VALUES (?, ?, ?)");
		final String sql = sqlBuilder.toString();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.getJdbcTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
	            ps.setObject(1, memberFollow.getMemberId());
	            ps.setObject(2, memberFollow.getFollowMemberId());
	            ps.setObject(3, memberFollow.getCreateTime());
	            return ps;
			}
			
		}, keyHolder);
		return keyHolder.getKey().longValue();
	}

	@Override
	public Long getIdByMemberIdAndFollowMemberId(Integer shardId, String memberId, String followMemberId) {
		Assert.notNull(shardId);
		Assert.notNull(memberId);
		Assert.notNull(followMemberId);
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT id FROM ali_member_follow_").append(shardId).append("_").append(getTableId(memberId)).append(" WHERE member_id = ? and follow_member_id = ?");
		final String sql = sqlBuilder.toString();
		List<Long> result = getJdbcTemplate().queryForList(sql, Long.class, memberId, followMemberId);
		if (result.size() == 0) {
			return null;
		}
		return result.get(0);
	}
	
	@Override
	public void createMemberFollowTable(Integer shardId, String memberId) {
		Assert.notNull(shardId);
		Assert.notNull(memberId);
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append("ali_member_follow_").append(shardId).append("_").append(getTableId(memberId)).append(" ");
		sqlBuilder.append("( ");
		sqlBuilder.append("`id`  int UNSIGNED NOT NULL AUTO_INCREMENT, ");
		sqlBuilder.append("`member_id`  varchar(50) NOT NULL, ");
		sqlBuilder.append("`follow_member_id`  varchar(50) NOT NULL, ");
		sqlBuilder.append("`create_time`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00', ");
		sqlBuilder.append("PRIMARY KEY (`id`), ");
		sqlBuilder.append("UNIQUE INDEX `member_btree` (`member_id`, `follow_member_id`) USING BTREE ");
		sqlBuilder.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8; ");
		this.getJdbcTemplate().execute(sqlBuilder.toString());
	}

}
