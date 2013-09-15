package org.danielli.xultimate.crawler.support;

import java.util.Arrays;
import java.util.Comparator;

import org.danielli.xultimate.crawler.listener.AbstractListener;
import org.danielli.xultimate.util.Assert;

public class ListenerService {
	
	private final AbstractListener[] listeners;

	public ListenerService(AbstractListener[] listeners) {
		Assert.notEmpty(listeners);
		// 按优先级排序。
		Arrays.sort(listeners, new Comparator<AbstractListener>() {
			@Override
			public int compare(AbstractListener o1, AbstractListener o2) {
				return o2.getPriority() - o1.getPriority();
			}
		});
		this.listeners = listeners;
	}

	public void execute(String linkId, String linkUrl) {
		for (int i = listeners.length - 1; i >= 0; i--) {
			AbstractListener abstractListener = listeners[i];
			if (abstractListener.support(linkUrl)) {
				abstractListener.handle(linkId, linkUrl);
			}
		}
	}
}
