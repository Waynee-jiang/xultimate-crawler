package alibaba.dao;

import org.apache.ibatis.annotations.Param;

import alibaba.po.MemberImpressLabel;

public interface MemberImpressLabelDAO {
	/**
	 * 保存
	 */
	void saveMemberImpressLabel(@Param("shardId") Long shardId, @Param("memberImpressLabel") MemberImpressLabel memberImpressLabel);
}
