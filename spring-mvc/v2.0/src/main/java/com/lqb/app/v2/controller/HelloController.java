package com.lqb.app.v2.controller;

import com.lqb.app.v2.service.IHelloService;
import com.lqb.spring.v2.annotation.Autowired;
import com.lqb.spring.v2.annotation.Controller;
import com.lqb.spring.v2.annotation.RequestMapping;
import com.lqb.spring.v2.webmvc.servlet.ModelAndView;

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
        HashMap<String, Object> model = new HashMap<>();
        String res = helloService.hello();
        model.put("data1", res);
        model.put("data2", "world");
        return new ModelAndView("test", model);
    }


}
