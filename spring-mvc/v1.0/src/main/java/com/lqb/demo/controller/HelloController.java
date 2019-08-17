package com.lqb.demo.controller;

import com.lqb.demo.service.IHelloService;
import com.lqb.mvcframework.annotation.Autowired;
import com.lqb.mvcframework.annotation.Controller;
import com.lqb.mvcframework.annotation.RequestMapping;
import com.lqb.mvcframework.annotation.RequestParam;

/**
 * @author liqibo
 * @description TODO
 * @date 2019/8/15 16:24
 **/
@Controller
public class HelloController {

    @Autowired
    private IHelloService helloService;

    @RequestMapping("/hello")
    public String hello(@RequestParam("name") String name, @RequestParam("age") Integer age) {
        return helloService.sayHello(name) + ". I'm " + age;
    }

}
