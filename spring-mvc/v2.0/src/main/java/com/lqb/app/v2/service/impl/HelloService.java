package com.lqb.app.v2.service.impl;

import com.lqb.app.v2.service.IHelloService;
import com.lqb.spring.v2.annotation.Service;

/**
 * @author liqibo
 * @description TODO
 * @date 2019/12/3 15:59
 **/
@Service
public class HelloService implements IHelloService {

    @Override
    public String hello() {
        return "hello";
    }

}
