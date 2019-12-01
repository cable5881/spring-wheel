package com.lqb.spring.v2.context.support;

import com.lqb.spring.v2.context.ConfigurableApplicationContext;
import com.lqb.spring.v2.core.factory.ConfigurableListableBeanFactory;
import com.lqb.spring.v2.core.io.support.ResourcePatternResolver;

public abstract class AbstractApplicationContext implements ConfigurableApplicationContext {

    private ResourcePatternResolver resourcePatternResolver;

    @Override
    public void refresh() {
        ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

        postProcessBeanFactory(beanFactory);
    }

    protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
        //这里使用了委派设计模式，父类定义了抽象的refreshBeanFactory()方法，具体实现调用子类容器的refreshBeanFactory()方法
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        return beanFactory;
    }

    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {

    }

    @Override
    public Object getBean(String name) {
        return getBeanFactory().getBean(name);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) {
        return getBeanFactory().getBean(requiredType);
    }

    @Override
    public abstract ConfigurableListableBeanFactory getBeanFactory();

}
