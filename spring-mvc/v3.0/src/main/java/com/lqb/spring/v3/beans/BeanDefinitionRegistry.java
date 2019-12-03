package com.lqb.spring.v3.beans;

import com.lqb.spring.v3.beans.config.BeanDefinition;

public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
