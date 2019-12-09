package com.lqb.demo;

import com.lqb.springframework.context.ApplicationContext;
import com.lqb.springframework.context.support.DefaultApplicationContext;

public class Main {
    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new DefaultApplicationContext("application.properties");
        IHelloService helloService = (IHelloService) applicationContext.getBean("helloService");
        helloService.sayHello();
    }
}
