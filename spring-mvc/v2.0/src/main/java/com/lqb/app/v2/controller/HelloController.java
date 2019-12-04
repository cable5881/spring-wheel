package com.lqb.app.v2.controller;

import com.lqb.app.v2.service.IHelloService;
import com.lqb.spring.v2.annotation.Autowired;
import com.lqb.spring.v2.annotation.Controller;
import com.lqb.spring.v2.annotation.RequestMapping;
import com.lqb.spring.v2.webmvc.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;

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
    public ModelAndView hello() {
        String res = helloService.hello();
        HashMap<String, Object> model = new HashMap<>();
        model.put("teacher", res);
        model.put("data", new Date());
        return new ModelAndView("first", model);
    }


}
