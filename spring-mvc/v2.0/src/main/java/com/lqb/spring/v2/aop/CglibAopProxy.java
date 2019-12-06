package com.lqb.spring.v2.aop;

import com.lqb.spring.v2.aop.support.AdvisedSupport;

/**
 * Created by Tom on 2019/4/14.
 */
public class CglibAopProxy implements AopProxy {
    public CglibAopProxy(AdvisedSupport config) {
    }

    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return null;
    }
}
