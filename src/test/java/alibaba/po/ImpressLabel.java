package alibaba.po;

import java.io.Serializable;
import java.util.Date;

public class ImpressLabel implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;		// 	ID
	
	private String labelName;	// 标签名称
	
	private Date createTime;	// 创建时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
