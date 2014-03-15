package alibaba.dao;

import org.apache.ibatis.annotations.Param;

import alibaba.po.MemberFollow;

public interface MemberFollowDAO {
	
	void saveMemberFollow(@Param("memberShardId") Long memberShardId, @Param("memberFollow")  MemberFollow memberFollow);
}
