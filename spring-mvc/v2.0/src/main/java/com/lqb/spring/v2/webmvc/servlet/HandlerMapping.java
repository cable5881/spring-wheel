package com.lqb.spring.v2.webmvc.servlet;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @author liqibo
 * @description TODO
 * @date 2019/12/3 16:49
 **/
public class HandlerMapping {
    private Object controller;    //保存方法对应的实例
    private Method method;        //保存映射的方法
    private Pattern pattern;    //URL的正则匹配

    public HandlerMapping(Pattern pattern, Object controller, Method method) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }
}
