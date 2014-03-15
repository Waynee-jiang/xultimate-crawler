package alibaba.po;

import java.io.Serializable;
import java.util.Date;

public class MemberFollow implements Serializable {
	private static final long serialVersionUID = 8209532281466172790L;
	
	private Long memberId;
	
	private Long followMemberId;
	
	private Date createTime;

	public Long getFollowMemberId() {
		return followMemberId;
	}

	public void setFollowMemberId(Long followMemberId) {
		this.followMemberId = followMemberId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
