package alibaba.dao;

import org.apache.ibatis.annotations.Param;
import org.danielli.xultimate.orm.mybatis.MyBatisRepository;

import alibaba.po.Member;

@MyBatisRepository
public interface MemberDAO {

	/***
	 * 保存
	 */
	void saveMember(@Param("shardId") Long shardId, @Param("member")  Member member);
}
