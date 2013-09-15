package org.danielli.xultimate.crawler.alibaba.model;

import java.io.Serializable;
import java.util.Date;

public class MemberFollow implements Serializable {
	private static final long serialVersionUID = 8209532281466172790L;

	private Long id;
	
	private String memberId;
	
	private String followMemberId;
	
	private Date createTime;

	public String getFollowMemberId() {
		return followMemberId;
	}

	public void setFollowMemberId(String followMemberId) {
		this.followMemberId = followMemberId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
