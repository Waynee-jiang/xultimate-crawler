package alibaba.dao;

import org.apache.ibatis.annotations.Param;
import org.danielli.xultimate.orm.mybatis.MyBatisRepository;

import alibaba.po.MemberFan;

@MyBatisRepository
public interface MemberFanDAO {
	
	void saveMemberFan(@Param("memberShardId") Long memberShardId, @Param("memberFan") MemberFan memberFan);
}
