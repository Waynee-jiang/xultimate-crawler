package org.danielli.xultimate.crawler.support;

import java.util.List;

import org.danielli.xultimate.context.util.ApplicationContextUtils;
import org.danielli.xultimate.context.util.BeanFactoryContext;
import org.danielli.xultimate.crawler.support.listener.AbstractListener;
import org.danielli.xultimate.util.ArrayUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Service;

@Service("listeners")
public class ListenersFactoryBean implements FactoryBean<AbstractListener[]> {

	@Override
	public AbstractListener[] getObject() throws Exception {
		List<AbstractListener> listeners = ApplicationContextUtils.getBeansOfType(BeanFactoryContext.currentApplicationContext(), AbstractListener.class);
		return listeners.toArray(ArrayUtils.newInstance(AbstractListener.class, listeners.size()));
	}

	@Override
	public Class<?> getObjectType() {
		return AbstractListener[].class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
