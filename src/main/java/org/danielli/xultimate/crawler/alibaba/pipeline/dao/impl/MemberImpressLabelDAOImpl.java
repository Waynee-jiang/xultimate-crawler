package org.danielli.xultimate.crawler.alibaba.pipeline.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.danielli.xultimate.crawler.alibaba.model.MemberImpressLabel;
import org.danielli.xultimate.crawler.alibaba.pipeline.dao.MemberImpressLabelDAO;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository("alibabaMemberImpressLabelDAO")
public class MemberImpressLabelDAOImpl extends BaseDAOImpl implements MemberImpressLabelDAO {

	@Override
	public void saveMemberImpressLabel(final MemberImpressLabel memberImpressLabel) {
		Assert.notNull(memberImpressLabel);
		final String sql = "INSERT INTO ali_member_impress_label (member_id, impress_label_id, create_time) VALUES (?, ?, ?)";
		this.getAlibabaJdbcTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql);
	            ps.setObject(1, memberImpressLabel.getMemberId());
	            ps.setObject(2, memberImpressLabel.getImpressLabelId());
	            ps.setObject(3, memberImpressLabel.getCreateTime());
	            return ps;
			}
			
		});
	}

}
