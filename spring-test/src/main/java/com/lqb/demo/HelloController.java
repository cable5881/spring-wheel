package com.lqb.demo;


import com.lqb.springframework.annotation.Controller;
import com.lqb.springframework.annotation.RequestMapping;
import com.lqb.springframework.webmvc.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class HelloController {

    @RequestMapping("/hello")
    public ModelAndView hello() {
        HashMap<String, Object> model = new HashMap<>();
        model.put("data1", "hello");
        model.put("data2", "world");
        return new ModelAndView("test", model);
    }


}
