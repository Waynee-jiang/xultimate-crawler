package alibaba.dao;

import org.apache.ibatis.annotations.Param;

import alibaba.po.WorkExp;

public interface WorkExpDAO {
	/**
	 * 保存
	 */
	void saveWorkExp(@Param("shardId") Long shardId, @Param("workExp") WorkExp workExp);
}
