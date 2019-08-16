package com.lqb.mvcframework.servlet;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author liqibo
 * @description TODO
 * @date 2019/8/15 21:13
 **/
@Data
public class HandlerMapping {
    private String className;
    private Method method;
}
