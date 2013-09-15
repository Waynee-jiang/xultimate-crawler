package org.danielli.xultimate.crawler.pipeline.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.danielli.xultimate.crawler.model.Member;
import org.danielli.xultimate.crawler.pipeline.dao.MemberDAO;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class MemberDAOImpl extends BaseDAOImpl implements MemberDAO {

	public void saveMember(Integer shardId, final Member member) {
		Assert.notNull(shardId);
		Assert.notNull(member);
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("INSERT INTO ali_member_").append(shardId).append(" (id, alibaba_id, name, sex, identity, email, phone, telephone, fax, ");
		sqlBuilder.append("live_province, live_city, address, pcode, position, company_name, company_url_in_alibaba, credit_year, head_image_url, ");
		sqlBuilder.append("birthday, blood_type, hometown, income, educat, religion, profile, interests, self_intr, member_acc, ");
		sqlBuilder.append("register_time, last_login, create_time, update_time) ");
		sqlBuilder.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		final String sql = sqlBuilder.toString();

		this.getJdbcTemplate().update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql);
	            ps.setObject(1, member.getId());
	            ps.setObject(2, member.getAlibabaId());
	            ps.setObject(3, member.getName());
	            ps.setObject(4, member.getSex() == null ? null : member.getSex().ordinal());
	            ps.setObject(5, member.getIdentity() == null ? null : member.getIdentity().ordinal());
	            ps.setObject(6, member.getEmail());
	            ps.setObject(7, member.getPhone());
	            ps.setObject(8, member.getTelephone());
	            ps.setObject(9, member.getFax());
	            ps.setObject(10, member.getLiveProvince());
	            ps.setObject(11, member.getLiveCity());
	            ps.setObject(12, member.getAddress());
	            ps.setObject(13, member.getPcode());
	            ps.setObject(14, member.getPosition());
	            ps.setObject(15, member.getCompanyName());
	            ps.setObject(16, member.getCompanyUrlInAlibaba());
	            ps.setObject(17, member.getCreditYear());
	            ps.setObject(18, member.getHeadImageUrl());
	            ps.setObject(19, member.getBirthday());
	            ps.setObject(20, member.getBloodType() == null ? null : member.getBloodType().ordinal());
	            ps.setObject(21, member.getHometown());
	            ps.setObject(22, member.getIncome() == null ? null : member.getIncome().ordinal());
	            ps.setObject(23, member.getEducat() == null ? null : member.getEducat().ordinal());
	            ps.setObject(24, member.getReligion() == null ? null : member.getReligion().ordinal());
	            ps.setObject(25, member.getProfile());
	            ps.setObject(26, member.getInterests());
	            ps.setObject(27, member.getSelfIntr());
	            ps.setObject(28, member.getMemberAcc());
	            ps.setObject(29, member.getRegistTime());
	            ps.setObject(30, member.getLastLogin());
	            ps.setObject(31, member.getCreateTime());
	            ps.setObject(32, member.getUpdateTime());
	            return ps;
			}
		});
	}
	
	@Override
	public Long getIdByAlibabaId(Integer shardId, String alibabaId) {
		String sql = new StringBuffer("SELECT id FROM ali_member_").append(shardId).append(" WHERE alibaba_id = ?").toString();
		List<Long> integers = this.getJdbcTemplate().queryForList(sql, Long.class, alibabaId);
		if (integers.size() == 0)
			return null;
		return integers.get(0);
	}
	
	@Override
	public void createMemberTable(Integer shardId) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("CREATE TABLE IF NOT EXISTS ali_member_").append(shardId).append(" (");
			sqlBuilder.append("`id`  int UNSIGNED NOT NULL , ");
			sqlBuilder.append("`alibaba_id`  varchar(50) NOT NULL ,");
			sqlBuilder.append("`name`  varchar(100) NULL ,");
			sqlBuilder.append("`sex`  int NULL ,");
			sqlBuilder.append("`identity`  int NULL ,");
			sqlBuilder.append("`email`  varchar(60) NULL ,");
			sqlBuilder.append("`phone`  varchar(60) NULL ,");
			sqlBuilder.append("`telephone`  varchar(60) NULL ,");
			sqlBuilder.append("`fax`  varchar(60) NULL ,");
			sqlBuilder.append("`live_province`  varchar(120) NULL ,");
			sqlBuilder.append("`live_city`  varchar(120) NULL ,");		
			sqlBuilder.append("`address`  varchar(240) NULL ,");
			sqlBuilder.append("`pcode`  varchar(60) NULL ,");
			sqlBuilder.append("`position`  varchar(150) NULL ,");
			sqlBuilder.append("`company_name`  varchar(300) NULL ,");
			sqlBuilder.append("`company_url_in_alibaba`  varchar(300) NULL ,");
			sqlBuilder.append("`credit_year`  int NULL ,");
			sqlBuilder.append("`head_image_url`  varchar(300) NULL ,");
			sqlBuilder.append("`birthday`  datetime NULL DEFAULT '0000-00-00 00:00:00' ,");
			sqlBuilder.append("`blood_type`  int NULL ,");
			sqlBuilder.append("`hometown`  varchar(30) NULL ,");
			sqlBuilder.append("`income`  int NULL ,");
			sqlBuilder.append("`educat`  int NULL ,");
			sqlBuilder.append("`religion`  int NULL ,");
			sqlBuilder.append("`profile`  varchar(120) NULL ,");
			sqlBuilder.append("`interests`  varchar(200) NULL ,");
			sqlBuilder.append("`self_intr`  varchar(3200) NULL ,");
			sqlBuilder.append("`member_acc`  varchar(60) NULL ,");
			sqlBuilder.append("`register_time`  datetime NULL DEFAULT '0000-00-00 00:00:00' ,");
			sqlBuilder.append("`last_login`  datetime NULL DEFAULT '0000-00-00 00:00:00' ,");
			sqlBuilder.append("`create_time`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ,");
			sqlBuilder.append("`update_time`  timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP ,");
			sqlBuilder.append("PRIMARY KEY (`id`),");
			sqlBuilder.append("UNIQUE INDEX `alibaba_id_btree` (`alibaba_id`) USING BTREE ) ENGINE=InnoDB DEFAULT CHARSET=utf8; ");
		this.getJdbcTemplate().execute(sqlBuilder.toString());
	}

}
