package alibaba.dao;

import org.apache.ibatis.annotations.Param;

import alibaba.po.EduExp;

public interface EduExpDAO {

	/**
	 * 保存
	 */
	void saveEduExp(@Param("shardId") Long shardId, @Param("eduExp") EduExp eduExp);
}
