package org.danielli.xultimate.crawler.alibaba.pipeline.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.danielli.xultimate.crawler.alibaba.model.MemberFan;
import org.danielli.xultimate.crawler.alibaba.pipeline.dao.MemberFanDAO;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository("alibabaMemberFanDAO")
public class MemberFanDAOImpl extends BaseDAOImpl implements MemberFanDAO {

	private Integer getTableId(String memberId) {
		return ((int) memberId.charAt(3)) % 25;
	}
	
	@Override
	public Long saveMemberFan(Integer shardId, final MemberFan memberFan) {
		Assert.notNull(shardId);
		Assert.notNull(memberFan);
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("INSERT INTO ali_member_fan_").append(shardId).append("_").append(getTableId(memberFan.getMemberId())).append(" ");
		sqlBuilder.append("(member_id, fan_member_id, create_time) VALUES (?, ?, ?)");
		final String sql = sqlBuilder.toString();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.getAlibabaJdbcTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
	            ps.setObject(1, memberFan.getMemberId());
	            ps.setObject(2, memberFan.getFanMemberId());
	            ps.setObject(3, memberFan.getCreateTime());
	            return ps;
			}
			
		}, keyHolder);
		return keyHolder.getKey().longValue();
	}

	@Override
	public Long getIdByMemberIdAndFanMemberId(Integer shardId, String memberId, String fanMemberId) {
		Assert.notNull(shardId);
		Assert.notNull(memberId);
		Assert.notNull(fanMemberId);
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT id FROM ali_member_fan_").append(shardId).append("_").append(getTableId(memberId)).append(" WHERE member_id = ? and fan_member_id = ?");
		final String sql = sqlBuilder.toString();
		List<Long> result = this.getAlibabaJdbcTemplate().queryForList(sql, Long.class, memberId, fanMemberId);
		if (result.size() == 0) {
			return null;
		}
		return result.get(0);
	}
	
	@Override
	public void createMemberFanTable(Integer shardId, String memberId) {
		Assert.notNull(shardId);
		Assert.notNull(memberId);
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append("ali_member_fan_").append(shardId).append("_").append(getTableId(memberId)).append(" ");
		sqlBuilder.append("( ");
		sqlBuilder.append("`id`  int UNSIGNED NOT NULL AUTO_INCREMENT, ");
		sqlBuilder.append("`member_id`  varchar(50) NOT NULL, ");
		sqlBuilder.append("`fan_member_id`  varchar(50) NOT NULL, ");
		sqlBuilder.append("`create_time`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00', ");
		sqlBuilder.append("PRIMARY KEY (`id`), ");
		sqlBuilder.append("UNIQUE INDEX `member_btree` (`member_id`, `fan_member_id`) USING BTREE ");
		sqlBuilder.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8; ");
		this.getAlibabaJdbcTemplate().execute(sqlBuilder.toString());
	}

}
