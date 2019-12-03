package com.lqb.spring.v3.context;

import com.lqb.spring.v3.core.factory.support.DefaultListableBeanFactory;

public interface ConfigurableApplicationContext extends ApplicationContext {

    void refresh();

    DefaultListableBeanFactory getBeanFactory();
}
