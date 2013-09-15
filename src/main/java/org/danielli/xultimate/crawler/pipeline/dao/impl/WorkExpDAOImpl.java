package org.danielli.xultimate.crawler.pipeline.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.danielli.xultimate.crawler.model.WorkExp;
import org.danielli.xultimate.crawler.pipeline.dao.WorkExpDAO;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class WorkExpDAOImpl extends BaseDAOImpl implements WorkExpDAO {

	@Override
	public Long saveWorkExp(final WorkExp workExp) {
		Assert.notNull(workExp);
		final String sql = "INSERT INTO ali_workexp (company_name, industry, scale, department, position, start_date, end_date, remark, member_id, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.getJdbcTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
	            ps.setObject(1, workExp.getCompanyName());
	            ps.setObject(2, workExp.getIndustry());
	            ps.setObject(3, workExp.getScale());
	            ps.setObject(4, workExp.getDepartment());
	            ps.setObject(5, workExp.getPosition());
	            ps.setObject(6, workExp.getStartDate());
	            ps.setObject(7, workExp.getEndDate());
	            ps.setObject(8, workExp.getRemark());
	            ps.setObject(9, workExp.getMemberId());
	            ps.setObject(10, workExp.getCreateTime());
	            return ps;
			}
			
		}, keyHolder);
		return keyHolder.getKey().longValue();
	}
	
	
	
}
