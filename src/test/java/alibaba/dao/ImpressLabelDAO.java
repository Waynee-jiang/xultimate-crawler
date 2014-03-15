package alibaba.dao;

import org.apache.ibatis.annotations.Param;
import org.danielli.xultimate.orm.mybatis.MyBatisRepository;

import alibaba.po.ImpressLabel;

@MyBatisRepository
public interface ImpressLabelDAO {

	/**
	 * 保存
	 */
	void saveImpressLabel(@Param("shardId") Long shardId, @Param("impressLabel") ImpressLabel impressLabel);
}
