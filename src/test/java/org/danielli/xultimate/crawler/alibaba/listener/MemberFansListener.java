package org.danielli.xultimate.crawler.alibaba.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.danielli.xultimate.crawler.alibaba.model.MemberFan;
import org.danielli.xultimate.crawler.alibaba.pipeline.PipelineService;
import org.danielli.xultimate.crawler2.support.LinkbaseHandlerSupport;
import org.danielli.xultimate.crawler2.support.urlresolver.JsoupAbstractUrlResolver;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.collections.CollectionUtils;
import org.danielli.xultimate.util.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service("alibabaMemberFansListener")
public class MemberFansListener extends JsoupAbstractUrlResolver {
	
	@Resource(name = "alibabaPipelineService")
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
	public void handle(Document document, String linkId, String linkUrl) throws Exception {
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
					linkUrlMap.put(LinkbaseHandlerSupport.LINK_COLUMN_NAME, url);
					linkUrlMap.put(LinkbaseHandlerSupport.PARENT_LINK_COLUMN_NAME, linkUrl);
					linkUrlMap.put(LinkbaseHandlerSupport.LINK_CREATE_TIME, System.currentTimeMillis());
					linkUrls.add(linkUrlMap);
				}
				if (!CollectionUtils.isEmpty(linkUrls))
					linkbaseHandler.addLinkUrls(linkUrls);
			}
		}
		
		Elements elements = document.select(".me-attention").select(".content.fd-clr").select(".mod.mod-pf-attention.fd-left");
		List<String> fanMemberIds = new ArrayList<String>();
		for (Element element : elements) {
			fanMemberIds.add(element.attr("data-box-config"));
		}
		
		if (fanMemberIds.size() == 0) {
			throw new Exception("页面解析有问题，没有相应的粉丝，怀疑页面不存在");
		}
		
		boolean saveSuccess = true;
		
		List<Map<String, Object>> linkUrls = new ArrayList<Map<String, Object>>();
		for (String fanId : fanMemberIds) {
			MemberFan memberFan = new MemberFan();
			memberFan.setCreateTime(new Date());
			memberFan.setMemberId(memberId);
			memberFan.setFanMemberId(fanId);
			
			String url = "http://me.1688.com/info/" + fanId + ".html";
			Map<String, Object> linkUrlMap = new HashMap<>();
			linkUrlMap.put(LinkbaseHandlerSupport.LINK_COLUMN_NAME, url);
			linkUrlMap.put(LinkbaseHandlerSupport.PARENT_LINK_COLUMN_NAME, linkUrl);
			linkUrlMap.put(LinkbaseHandlerSupport.LINK_CREATE_TIME, System.currentTimeMillis());
			linkUrls.add(linkUrlMap);
			try {
				pipelineService.saveMemberFan(memberFan);
				LOGGER.info("保存用户[" + memberId + "]的粉丝[" + memberFan.getFanMemberId() + "]成功");
			} catch (Exception e) {
				LOGGER.error("保存用户[" + memberId + "]的粉丝[" + memberFan.getFanMemberId() + "]失败:" + e.getMessage(), e);
				saveSuccess = false;
			}
		}
		
		if (!CollectionUtils.isEmpty(linkUrls))
			linkbaseHandler.addLinkUrls(linkUrls);
		
		if (!saveSuccess) {
			throw new Exception();
		}
	}
}
