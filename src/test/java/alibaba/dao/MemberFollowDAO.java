package alibaba.dao;

import org.apache.ibatis.annotations.Param;
import org.danielli.xultimate.orm.mybatis.MyBatisRepository;

import alibaba.po.MemberFollow;

@MyBatisRepository
public interface MemberFollowDAO {
	
	void saveMemberFollow(@Param("memberShardId") Long memberShardId, @Param("memberFollow")  MemberFollow memberFollow);
}
