package alibaba.dao;

import org.apache.ibatis.annotations.Param;

import alibaba.po.Member;

public interface MemberDAO {

	/***
	 * 保存
	 */
	void saveMember(@Param("shardId") Long shardId, @Param("member")  Member member);
}
