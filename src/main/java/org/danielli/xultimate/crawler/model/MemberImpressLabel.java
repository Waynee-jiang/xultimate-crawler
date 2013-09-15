package org.danielli.xultimate.crawler.model;

import java.io.Serializable;
import java.util.Date;

public class MemberImpressLabel implements Serializable {
	private static final long serialVersionUID = 5871814701670719793L;

	private String memberId;		// 会员ID
	
	private Long impressLabelId;	// 印像标签ID
	
	private Date createTime;		// 创建时间

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Long getImpressLabelId() {
		return impressLabelId;
	}

	public void setImpressLabelId(Long impressLabelId) {
		this.impressLabelId = impressLabelId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
