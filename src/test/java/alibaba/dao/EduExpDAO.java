package alibaba.dao;

import org.apache.ibatis.annotations.Param;
import org.danielli.xultimate.orm.mybatis.MyBatisRepository;

import alibaba.po.EduExp;

@MyBatisRepository
public interface EduExpDAO {

	/**
	 * 保存
	 */
	void saveEduExp(@Param("shardId") Long shardId, @Param("eduExp") EduExp eduExp);
}
