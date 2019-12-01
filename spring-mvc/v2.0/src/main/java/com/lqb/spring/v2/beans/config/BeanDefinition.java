package com.lqb.spring.v2.beans.config;

import com.sun.istack.internal.Nullable;

public interface BeanDefinition {

    void setLazyInit(boolean lazyInit);

    boolean isLazyInit();

    void setBeanClassName(String beanClassName);

    String getBeanClassName();

    void setFactoryBeanName(@Nullable String factoryBeanName);

    String getFactoryBeanName();
}
