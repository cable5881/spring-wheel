package com.lqb.app.v3.controller;

import com.lqb.app.v3.service.IHelloService;
import com.lqb.spring.v3.annotation.Autowired;
import com.lqb.spring.v3.annotation.Controller;
import com.lqb.spring.v3.annotation.RequestMapping;

/**
 * @author liqibo
 * @description TODO
 * @date 2019/12/3 13:06
 **/
@Controller
public class HelloController {

    @Autowired
    private IHelloService helloService;

    @RequestMapping("/hello")
    public String hello() {
        return helloService.hello();
    }


}
