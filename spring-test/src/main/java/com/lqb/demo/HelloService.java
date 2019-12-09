package com.lqb.demo;

import com.lqb.springframework.annotation.Autowired;
import com.lqb.springframework.annotation.Service;

/**
 * @author liqibo
 * @description TODO
 * @date 2019/12/9 15:33
 **/
@Service
public class HelloService implements IHelloService{

    @Autowired
    private MotherService motherService;

    public void sayHello() {
        System.out.println("hello world!");
        System.out.println(motherService.call());
    }
}
