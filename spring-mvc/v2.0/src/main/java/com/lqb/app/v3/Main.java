package com.lqb.app.v3;

import com.lqb.app.v3.controller.HelloController;
import com.lqb.spring.v3.context.support.DefaultApplicationContext;

/**
 * @author liqibo
 * @description TODO
 * @date 2019/12/3 13:07
 **/
public class Main {
    public static void main(String[] args) throws Exception {
        DefaultApplicationContext applicationContext = new DefaultApplicationContext("application.properties");
        HelloController helloController = (HelloController)applicationContext.getBean("helloController");
        System.out.println(helloController.hello());
    }
}
