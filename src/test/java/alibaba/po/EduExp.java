package alibaba.po;

import java.io.Serializable;
import java.util.Date;

import alibaba.po.Member.EducatType;

public class EduExp implements Serializable {
	private static final long serialVersionUID = 5365576927170156421L;

	private Long id;				// ID
	
	private String schoolName;		// 学校名称
	
	private EducatType educat;		// 学位
	
	private String specialty;		// 专业
	
	private Date startDate;			// 开始时间
	
	private Date endDate;			// 结束时间
	
	private Long memberId;		// 会员ID
	
	private Date createTime;		// 创建时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public EducatType getEducat() {
		return educat;
	}

	public void setEducat(EducatType educat) {
		this.educat = educat;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
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
