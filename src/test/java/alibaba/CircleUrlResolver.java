package alibaba;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.danielli.xultimate.crawler.support.LinkbaseHandlerSupport;
import org.danielli.xultimate.crawler.support.urlresolver.JsoupAbstractUrlResolver;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.collections.CollectionUtils;
import org.danielli.xultimate.util.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service("circleUrlResolver")
public class CircleUrlResolver extends JsoupAbstractUrlResolver {
	
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
	public void handle(Document document, String linkId, String linkUrl) throws Exception {
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
							linkUrlMap.put(LinkbaseHandlerSupport.LINK_COLUMN_NAME, url);
							linkUrlMap.put(LinkbaseHandlerSupport.PARENT_LINK_COLUMN_NAME, linkUrl);
							linkUrlMap.put(LinkbaseHandlerSupport.LINK_CREATE_TIME, System.currentTimeMillis());
							linkUrls.add(linkUrlMap);
						}
						if (!CollectionUtils.isEmpty(linkUrls))
							linkbaseHandler.addLinkUrls(linkUrls);
					}
				} else {
					LOGGER.warn("解析{}:{}最大页数失败", linkId, linkUrl);
				}
			} else {
				LOGGER.warn("解析{}:{}没有最大页数", linkId, linkUrl);
			}
		}
		
		Elements elements = document.select(".memberlist.fd-clr");
		List<Map<String, Object>> linkUrls = new ArrayList<Map<String, Object>>();
		for (Element element : elements) {
			Elements liElements = element.children();
			for (Element liElement : liElements) {
				String memberid = liElement.select(".fcstate-notyet").select(".btn-follow").attr("data-memberid");
				if (StringUtils.isNotBlank(memberid)) {
					String url = "http://me.1688.com/info/" + memberid + ".html";
					Map<String, Object> linkUrlMap = new HashMap<>();
					linkUrlMap.put(LinkbaseHandlerSupport.LINK_COLUMN_NAME, url);
					linkUrlMap.put(LinkbaseHandlerSupport.PARENT_LINK_COLUMN_NAME, linkUrl);
					linkUrlMap.put(LinkbaseHandlerSupport.LINK_CREATE_TIME, System.currentTimeMillis());
					linkUrls.add(linkUrlMap);
				} else {
					LOGGER.warn("解析{}:{}没有最大页数页面中会员失败", linkId, linkUrl);
				}
			}
		}
		
		if (!CollectionUtils.isEmpty(linkUrls)) {
			linkbaseHandler.addLinkUrls(linkUrls);
		}
	}
}
