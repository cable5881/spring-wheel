package com.lqb.demo.service.impl;

import com.lqb.demo.service.IHelloService;
import com.lqb.mvcframework.annotation.Service;

/**
 * @author liqibo
 * @description TODO
 * @date 2019/8/15 16:23
 **/
@Service
public class HelloServiceImpl implements IHelloService {
    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }
}
