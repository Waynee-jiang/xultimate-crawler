package alibaba.dao;

import org.apache.ibatis.annotations.Param;
import org.danielli.xultimate.orm.mybatis.MyBatisRepository;

import alibaba.po.MemberImpressLabel;

@MyBatisRepository
public interface MemberImpressLabelDAO {
	/**
	 * 保存
	 */
	void saveMemberImpressLabel(@Param("shardId") Long shardId, @Param("memberImpressLabel") MemberImpressLabel memberImpressLabel);
}
