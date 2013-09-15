package org.danielli.xultimate.crawler.alibaba.model;

import java.io.Serializable;
import java.util.Date;

public class MemberShard implements Serializable {
	private static final long serialVersionUID = 347981743921531769L;

	private String memberId;
	
	private Integer shardId;
	
	private Date createTime;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Integer getShardId() {
		return shardId;
	}

	public void setShardId(Integer shardId) {
		this.shardId = shardId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
