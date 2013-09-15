package org.danielli.xultimate.crawler.pipeline.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.danielli.xultimate.crawler.model.EduExp;
import org.danielli.xultimate.crawler.pipeline.dao.EduExpDAO;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class EduExpDAOImpl extends BaseDAOImpl implements EduExpDAO {

	@Override
	public Long saveEduExp(final EduExp eduExp) {
		Assert.notNull(eduExp);
		final String sql = "INSERT INTO ali_eduexp (school_name, educat, specialty, start_date, end_date, member_id, create_time) VALUES (?, ?, ?, ?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.getJdbcTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
	            ps.setObject(1, eduExp.getSchoolName());
	            ps.setObject(2, eduExp.getEducat() == null ? null : eduExp.getEducat().ordinal());
	            ps.setObject(3, eduExp.getSpecialty());
	            ps.setObject(4, eduExp.getStartDate());
	            ps.setObject(5, eduExp.getEndDate());
	            ps.setObject(6, eduExp.getMemberId());
	            ps.setObject(7, eduExp.getCreateTime());
	            return ps;
			}
			
		}, keyHolder);
		return keyHolder.getKey().longValue();
	}

}
