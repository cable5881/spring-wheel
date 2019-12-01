package com.lqb.spring.v2.context;

import com.lqb.spring.v2.core.factory.ConfigurableListableBeanFactory;

public interface ConfigurableApplicationContext extends ApplicationContext {

    void refresh();

    ConfigurableListableBeanFactory getBeanFactory();
}
