package com.lqb.spring.v2.aop.intercept;

/**
 * Created by Tom on 2019/4/14.
 */
public interface MethodInterceptor {
    Object invoke(MethodInvocation invocation) throws Throwable;
}
