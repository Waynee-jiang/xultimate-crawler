package alibaba.dao;

import org.apache.ibatis.annotations.Param;
import org.danielli.xultimate.orm.mybatis.MyBatisRepository;

import alibaba.po.WorkExp;

@MyBatisRepository
public interface WorkExpDAO {
	/**
	 * 保存
	 */
	void saveWorkExp(@Param("shardId") Long shardId, @Param("workExp") WorkExp workExp);
}
