package alibaba.dao;

import org.apache.ibatis.annotations.Param;

import alibaba.po.MemberFan;

public interface MemberFanDAO {
	
	void saveMemberFan(@Param("memberShardId") Long memberShardId, @Param("memberFan") MemberFan memberFan);
}
