package com.lqb.spring.v2.core.factory.support;

import com.lqb.spring.v2.beans.config.BeanDefinition;
import com.lqb.spring.v2.core.factory.ConfigurableListableBeanFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory implements ConfigurableListableBeanFactory {

    //存储注册信息的BeanDefinition
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    @Override
    public Object getBean(String name) {
        return null;
    }

    @Override
    public <T> T getBean(Class<T> requiredType) {
        return null;
    }

}
