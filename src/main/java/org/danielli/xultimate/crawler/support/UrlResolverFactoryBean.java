package org.danielli.xultimate.crawler.support;

import java.util.Collection;

import org.danielli.xultimate.context.util.ApplicationContextUtils;
import org.danielli.xultimate.context.util.BeanFactoryContext;
import org.danielli.xultimate.crawler.support.urlresolver.AbstractUrlResolver;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

/**
 * {@code Collection<AbstractUrlResolver> }的Factory Bean形式。
 * 
 * @author Daniel Li
 * @since 12 Jun 2013
 */
@Service("UrlResolverCollection")
@DependsOn("beanFactoryContext")
public class UrlResolverFactoryBean implements FactoryBean<Collection<AbstractUrlResolver>> {

	@Override
	public Collection<AbstractUrlResolver> getObject() throws Exception {
		Collection<AbstractUrlResolver> listeners = ApplicationContextUtils.getBeansOfType(BeanFactoryContext.currentApplicationContext(), AbstractUrlResolver.class);
		return listeners;
	}

	@Override
	public Class<?> getObjectType() {
		return Collection.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
