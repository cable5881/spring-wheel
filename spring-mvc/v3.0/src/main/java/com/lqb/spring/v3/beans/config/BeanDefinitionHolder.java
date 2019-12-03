package com.lqb.spring.v3.beans.config;

import lombok.Data;

@Data
public class BeanDefinitionHolder {

    private final BeanDefinition beanDefinition;

    private final String beanName;

    public BeanDefinitionHolder(BeanDefinition beanDefinition, String beanName) {
        this.beanDefinition = beanDefinition;
        this.beanName = beanName;
    }

}
