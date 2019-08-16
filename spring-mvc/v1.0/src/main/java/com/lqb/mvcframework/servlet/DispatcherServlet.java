package com.lqb.mvcframework.servlet;

import com.lqb.mvcframework.annotation.Autowired;
import com.lqb.mvcframework.annotation.Component;
import com.lqb.mvcframework.annotation.Controller;
import com.lqb.mvcframework.annotation.RequestMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author liqibo
 * @description TODO
 * @date 2019/8/15 15:30
 **/
public class DispatcherServlet extends HttpServlet {

    private Properties configs;

    private Map<String, Object> components = new HashMap<>();

    private Map<String, HandlerMapping> urlMappings = new HashMap<>();

    private String scanPackage;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String requestUri = req.getRequestURI();
            PrintWriter writer = resp.getWriter();
            if (!urlMappings.containsKey(requestUri)) {
                writer.write("404");
                writer.flush();
                writer.close();
                return;
            }

            HandlerMapping handlerMapping = urlMappings.get(requestUri);
            Object o = components.get(handlerMapping.getClassName());
            Method method = handlerMapping.getMethod();
            if (method.getParameterCount() == 0) {
                Object result = method.invoke(o, (Object) null);
                renderResponse(resp, writer, result);
                return;
            }

            Parameter[] params = method.getParameters();
            Object[] paramObjs = new Object[params.length];
            Map parameterMap = req.getParameterMap();
            for (int i = 0; i < params.length; i++) {
                //IDEA 需要配置-parameters在Preferences->Build,Execution,Deployment->Compiler->java Compiler
                //否则获取不到参数名。或者适用cglib或者asm等第三方jar包来获取
                paramObjs[i] = parameterMap.getOrDefault(params[i].getName(), null);
            }

            Object result = method.invoke(o, paramObjs);
            renderResponse(resp, writer, result);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void renderResponse(HttpServletResponse resp, PrintWriter writer, Object result) throws IOException {
        if (result instanceof Void) {
            writer.flush();
            writer.close();
            return;
        }

        ServletOutputStream outputStream = resp.getOutputStream();
        if (result instanceof String) {
            outputStream.write(((String)result).getBytes());
            writer.flush();
            writer.close();
            return;
        } else {
            //TODO 渲染HTML
            return;
        }
    }

    @Override
    public void init(ServletConfig config) {
        configs = new Properties();
        try {
            configs.load(this.getClass().getResourceAsStream("/" + config.getInitParameter("contextConfigLocation")));
            scanPackage = configs.getProperty("scanPackage");
            initClass(scanPackage);

            //保证所有被管理的类先实例化(initClass)，再去填充它的Autowire属性
            //如果放在上一个循环,则可能发生有的service没有被实例化却注入到属性的情况
            initClassProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initClass(String packagePath) {
        URL classDirUrl = this.getClass().getClassLoader().getResource(packagePath.replace(".", "/"));
        try {
            File classDir = new File(classDirUrl.toURI());
            for (File file : classDir.listFiles()) {
                if (file.isDirectory()) {
                    initClass(packagePath + "." + file.getName());
                    continue;
                }

                if (!file.getName().endsWith(".class")) {
                    continue;
                }

                //需要去掉末尾的.class才能读取到
                String className = packagePath + "." + file.getName().replace(".class", "");
                Class<?> clazz = Class.forName(className);
                Annotation[] annotations = clazz.getAnnotations();
                if (annotations.length == 0) {
                    continue;
                }

                for (Annotation annotation : annotations) {
                    Class<? extends Annotation> annotationType = annotation.annotationType();
                    if (annotationType.isAnnotationPresent(Component.class)) {
                        Object o = clazz.newInstance();
                        //如果存在接口则用接口类名作为key
                        Class<?>[] interfaces = clazz.getInterfaces();
                        if (interfaces.length > 0) {
                            for (Class<?> anInterface : interfaces) {
                                components.put(anInterface.getName(), o);
                            }
                        } else {
                            components.put(className, o);
                        }

                        //这里就不用annotationType.isAnnotationPresent了
                        if (annotationType == Controller.class) {
                            initUrlMapping(clazz);
                        }

                        break;
                    }
                }
            }
        } catch (URISyntaxException
                | ClassNotFoundException
                | IllegalAccessException
                | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void initClassProperties() {
        for (Object o : components.values()) {
            //getFields()无法获取到private域的字段
            Field[] fields = o.getClass().getDeclaredFields();
            for (Field field : fields) {
                Autowired autowired = field.getAnnotation(Autowired.class);
                if (autowired == null) {
                    continue;
                }
                try {
                    String fieldType = field.getType().getName();

                    if (!components.containsKey(fieldType)) {
                        continue;
                    }

                    field.setAccessible(true);
                    field.set(o, components.get(fieldType));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initUrlMapping(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
            if (annotation == null) {
                continue;
            }

            String url = annotation.value();
            if (url == null || url.trim().length() == 0) {
                continue;
            }

            HandlerMapping handlerMapping = new HandlerMapping();
            handlerMapping.setClassName(clazz.getCanonicalName());
            handlerMapping.setMethod(method);
            urlMappings.put(url, handlerMapping);
        }
    }

}
