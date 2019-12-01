package com.lqb.spring.v2.core.factory;

public interface BeanFactory {

    Object getBean(String name);

    <T> T getBean(Class<T> requiredType);
}
