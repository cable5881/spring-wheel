package com.lqb.spring.v2.beans.config;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BeanDefinition {

    private String beanClassName;

    private boolean lazyInit = false;

    private String factoryBeanName;

    public BeanDefinition() {
    }
}
