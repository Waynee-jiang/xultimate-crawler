package org.danielli.xultimate.crawler.listener.support;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.danielli.xultimate.crawler.listener.AbstractListener;
import org.danielli.xultimate.crawler.model.MemberFan;
import org.danielli.xultimate.crawler.pipeline.PipelineService;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.collections.CollectionUtils;
import org.danielli.xultimate.util.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberFansListener extends AbstractListener {
	
	@Autowired
	private PipelineService pipelineService;

	@Override
	public boolean support(String crawledeUrl) {
		return StringUtils.contains(crawledeUrl, "http://me.1688.com/fans/");
	}
	
	@Override
	public int getPriority() {
		return 1000;
	}
	
	@Override
	public void handle(Document document, String linkId, String linkUrl) {
		String memberId = document.select(".mod.mod-i-masthead").select(".set-form").select("input[name=memberId]").val();
		
		if (!StringUtils.contains(linkUrl, "page=")) {
			Elements elements = document.select(".me-attention").select(".f-tips-c").select(".f-txt");
			if (elements.size() > 0 && StringUtils.contains(elements.get(0).text(), "没有粉丝")) {
				return;
			}
			
			Integer fansCount = NumberUtils.toInt(document.select(".me-attention").select(".count.l-gray").select(".d-gray").get(0).text());
			if (fansCount > 0) {
				List<Map<String, Object>> linkUrls = new ArrayList<Map<String, Object>>();
				Integer maxPageNo = fansCount / 20 + 1;
				for (int i = 2; i <= maxPageNo; i++) {
					String url = linkUrl + "?page=" + i;
					Map<String, Object> linkUrlMap = new HashMap<>();
					linkUrlMap.put("linkUrl", url);
					linkUrlMap.put("parentLinkUrl", linkUrl);
					linkUrlMap.put("createTime", System.currentTimeMillis());
					linkUrls.add(linkUrlMap);
				}
				if (!CollectionUtils.isEmpty(linkUrls))
					linkbaseService.addLinkUrls(linkUrls);
			}
		}
		
		Elements elements = document.select(".me-attention").select(".content.fd-clr").select(".mod.mod-pf-attention.fd-left");
		List<String> fanMemberIds = new ArrayList<String>();
		for (Element element : elements) {
			fanMemberIds.add(element.attr("data-box-config"));
		}
		
		boolean saveSuccess = true;
		
		List<Map<String, Object>> linkUrls = new ArrayList<Map<String, Object>>();
		for (String fanId : fanMemberIds) {
			MemberFan memberFan = new MemberFan();
			memberFan.setCreateTime(new Date());
			memberFan.setMemberId(memberId);
			memberFan.setFanMemberId(fanId);
			
			String url = "http://me.1688.com/info/" + fanId + ".htm";
			Map<String, Object> linkUrlMap = new HashMap<>();
			linkUrlMap.put("linkUrl", url);
			linkUrlMap.put("parentLinkUrl", linkUrl);
			linkUrlMap.put("createTime", System.currentTimeMillis());
			linkUrls.add(linkUrlMap);
			try {
				pipelineService.saveMemberFan(memberFan);
				LOGGER.info("保存用户[" + memberId + "]的粉丝[" + memberFan.getFanMemberId() + "]成功");
			} catch (Exception e) {
				LOGGER.error("保存用户[" + memberId + "]的粉丝[" + memberFan.getFanMemberId() + "]失败:" + e.getMessage(), e);
				saveSuccess = false;
			}
		}
		
		try {
			if (!CollectionUtils.isEmpty(linkUrls))
				linkbaseService.addLinkUrls(linkUrls);
		} catch (Exception e) {
			LOGGER.error("添加URL失败：" + e.getMessage(), e);
		}
		
		if (!saveSuccess) {
			throw new RuntimeException();
		}
	}
}
