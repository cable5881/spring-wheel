package com.lqb.demo;

/**
 * @author liqibo
 * @description TODO
 * @date 2019/8/15 17:41
 **/
public class Application {
    public static void main(String[] args) throws ClassNotFoundException {
        Application demo = new Application();
//        try {
////            Class<?> clazz = demo.getClass().getClassLoader().loadClass("com.lqb.demo.controller.HelloController");
//            Class<?> clazz = demo.getClass().getClassLoader().loadClass("com.lqb.mvcframework.annotation.Controller");
//            System.out.println(clazz.isAnnotationPresent(Component.class));
//            System.out.println(demo.getClass().getClassLoader().loadClass("com.lqb.mvcframework.annotation.Component").isAnnotationPresent(Component.class));
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

//        Class<?> clazz = demo.getClass().getClassLoader().loadClass("com.lqb.demo.controller.HelloController");
//        if (clazz.getAnnotations()[0].annotationType().isAnnotationPresent(Component.class)) {
//            System.out.println(true);
//        }
//        System.out.println(clazz.getCanonicalName());
//        System.out.println(clazz.getName());
//        System.out.println(clazz.getSimpleName());
//        System.out.println(clazz.getTypeName());

        System.out.println(demo.getClass().getClassLoader().getResourceAsStream("application.properties"));
    }
}
