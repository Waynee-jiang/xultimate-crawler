package alibaba.po;

import java.io.Serializable;
import java.util.Date;

public class MemberFan  implements Serializable {
	private static final long serialVersionUID = 8209532281466172790L;
	
	private Long memberId;
	
	private Long fanMemberId;
	
	private Date createTime;

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

	public Long getFanMemberId() {
		return fanMemberId;
	}

	public void setFanMemberId(Long fanMemberId) {
		this.fanMemberId = fanMemberId;
	}
}