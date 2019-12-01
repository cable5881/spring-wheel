package com.lqb.spring.v2.context;

import com.lqb.spring.v2.beans.BeanDefinitionRegistry;
import com.lqb.spring.v2.beans.config.BeanDefinitionHolder;

import java.util.LinkedHashSet;
import java.util.Set;

public class ClassPathBeanDefinitionScanner {

    private final BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }


    public int scan(String... basePackages) {
        doScan(basePackages);
        return 0;
    }

    private Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = new LinkedHashSet<>();
        for (String basePackage : basePackages) {

        }
        return beanDefinitions;
    }
}
