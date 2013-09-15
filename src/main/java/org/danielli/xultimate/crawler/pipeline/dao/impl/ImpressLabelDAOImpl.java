package org.danielli.xultimate.crawler.pipeline.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.danielli.xultimate.crawler.model.ImpressLabel;
import org.danielli.xultimate.crawler.pipeline.dao.ImpressLabelDAO;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class ImpressLabelDAOImpl extends BaseDAOImpl implements ImpressLabelDAO {

	@Override
	public Long saveImpressLabel(final ImpressLabel impressLabel) {
		Assert.notNull(impressLabel);
		final String sql = "INSERT INTO ali_impress_label (label_name, create_time) VALUES (?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.getJdbcTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
	            ps.setObject(1, impressLabel.getLabelName());
	            ps.setObject(2, impressLabel.getCreateTime());
	            return ps;
			}
			
		}, keyHolder);
		return keyHolder.getKey().longValue();
	}
	
	@Override
	public Long getIdByLabelName(String labelName) {
		final String sql = "SELECT id FROM ali_impress_label WHERE label_name = ?";
		List<Long> result = getJdbcTemplate().queryForList(sql, Long.class, labelName);
		if (result.size() == 0) {
			return null;
		}
		return result.get(0);
	}

}
