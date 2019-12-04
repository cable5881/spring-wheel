package com.lqb.spring.v2.beans.config;

/**
 * @author liqibo
 * @description TODO
 * @date 2019/12/3 16:38
 **/
public class BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }

}
