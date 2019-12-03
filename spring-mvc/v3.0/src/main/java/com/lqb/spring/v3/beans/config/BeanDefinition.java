package com.lqb.spring.v3.beans.config;

import com.lqb.spring.v3.core.io.Resource;
import com.lqb.spring.v3.core.type.MetadataReader;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BeanDefinition {

    private String beanClassName;

    private boolean lazyInit = false;

    private String factoryBeanName;

    private Resource resource;


    public BeanDefinition(MetadataReader metadataReader) {

    }

    public BeanDefinition() {
    }
}
