package alibaba.biz;

import alibaba.po.ImpressLabel;

public interface ImpressLabelBiz {

	/**
	 * 保存
	 */
	void saveImpressLabel(ImpressLabel impressLabel);
	
	/**
	 * 获取ID
	 */
	Long getIdByLabelName(String labelName);
}
