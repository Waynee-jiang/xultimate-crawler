package org.danielli.xultimate.crawler2.support.urlresolver;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class JsoupAbstractUrlResolver extends RestTemplateAbstractUrlResolver {

	@Override
	public void handle(ResponseEntity<String> response, String linkId, String linkUrl) throws Exception {
		if (response.getStatusCode() == HttpStatus.OK) {
			if (StringUtils.isNotBlank(response.getBody())) {
				handle(Jsoup.parse(response.getBody()), linkId, linkUrl);
			} else {
				throw new Exception("响应体为空");
			}
		} else {
			throw new Exception("请求返回状态码错误: " + response.getStatusCode());
		}
		
	}
	
	public abstract void handle(Document document, String linkId, String linkUrl) throws Exception;
}
