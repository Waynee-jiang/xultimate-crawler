package org.danielli.xultimate.crawler.alibaba.model;

import java.io.Serializable;
import java.util.Date;

public class WorkExp implements Serializable {
	
	private static final long serialVersionUID = 790567108923136624L;

	private Long id;
	
	private String companyName;			// 公司名称
	
	private String industry;			// 所属行业
	
	private String scale;				// 公司规模
	
	private String department;			// 部门
	
	private String position;			// 职位
	
	private Date startDate;				// 开始时间
	
	private Date endDate;				// 结束时间
	
	private String remark;				// 工作内容
	
	private String memberId;			// 会员ID
	
	private Date createTime;		// 创建时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
