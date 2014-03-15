package alibaba.dao;

import org.apache.ibatis.annotations.Param;

import alibaba.po.ImpressLabel;

public interface ImpressLabelDAO {

	/**
	 * 保存
	 */
	void saveImpressLabel(@Param("shardId") Long shardId, @Param("impressLabel") ImpressLabel impressLabel);
}
