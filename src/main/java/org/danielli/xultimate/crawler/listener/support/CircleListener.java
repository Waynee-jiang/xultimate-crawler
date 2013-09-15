package org.danielli.xultimate.crawler.listener.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.danielli.xultimate.crawler.listener.AbstractListener;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.collections.CollectionUtils;
import org.danielli.xultimate.util.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CircleListener extends AbstractListener {
	
	private Pattern pattern = Pattern.compile("^共(.*)页$");
	
	@Override
	public boolean support(String crawledeUrl) {
		return StringUtils.contains(crawledeUrl, "http://quan.1688.com/member/");
	}

	@Override
	public int getPriority() {
		return 1000;
	}

	@Override
	public void handle(Document document, String linkId, String linkUrl) {
		if (!StringUtils.contains(linkUrl, "?p=")) {
			String text = document.select(".dpl-paging-total").text();
			if (StringUtils.isNotBlank(text)) {
				Matcher matcher = pattern.matcher(text);
				if (matcher.find()) {
					Integer maxPageNo = NumberUtils.toInt(matcher.group(1));
					if (maxPageNo > 0) {
						List<Map<String, Object>> linkUrls = new ArrayList<Map<String, Object>>();
						for (int i = 2; i <= maxPageNo; i++) {
							String url = linkUrl + "?p=" + i;
							Map<String, Object> linkUrlMap = new HashMap<>();
							linkUrlMap.put("linkUrl", url);
							linkUrlMap.put("parentLinkUrl", linkUrl);
							linkUrlMap.put("createTime", System.currentTimeMillis());
							linkUrls.add(linkUrlMap);
						}
						if (!CollectionUtils.isEmpty(linkUrls))
							linkbaseService.addLinkUrls(linkUrls);
					}
				} else {
					LOGGER.warn("解析ID为" + linkId + ", URL为" + linkUrl + "的页面最大页数失败");
				}
			} else {
				LOGGER.warn("解析ID为" + linkId + ", URL为" + linkUrl + "的页面没有最大页数");
			}
		}
		
		Elements elements = document.select(".memberlist.fd-clr");
		List<Map<String, Object>> linkUrls = new ArrayList<Map<String, Object>>();
		for (Element element : elements) {
			Elements liElements = element.children();
			for (Element liElement : liElements) {
				String memberid = liElement.select(".fcstate-notyet").select(".btn-follow").attr("data-memberid");
				if (StringUtils.isNotBlank(memberid)) {
					String url = "http://me.1688.com/info/" + memberid + ".htm";
					Map<String, Object> linkUrlMap = new HashMap<>();
					linkUrlMap.put("linkUrl", url);
					linkUrlMap.put("parentLinkUrl", linkUrl);
					linkUrlMap.put("createTime", System.currentTimeMillis());
					linkUrls.add(linkUrlMap);
				} else {
					LOGGER.warn("解析ID为" + linkId + ", URL为" + linkUrl + "的页面中会员ID:" + memberid + "失败");
				}
			}
		}
		
		try {
			if (!CollectionUtils.isEmpty(linkUrls))
				linkbaseService.addLinkUrls(linkUrls);
		} catch (Exception e) {
			LOGGER.error("添加URL失败：" + e.getMessage(), e);
		}
	}
}
