package com.lqb.spring.v2.context.support;

import com.lqb.spring.v2.annotation.Autowired;
import com.lqb.spring.v2.annotation.Controller;
import com.lqb.spring.v2.annotation.Service;
import com.lqb.spring.v2.beans.BeanWrapper;
import com.lqb.spring.v2.beans.config.BeanDefinition;
import com.lqb.spring.v2.beans.config.BeanPostProcessor;
import com.lqb.spring.v2.beans.support.BeanDefinitionReader;
import com.lqb.spring.v2.core.factory.BeanFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultApplicationContext implements BeanFactory {

    protected final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    //单例的IOC容器缓存
    private Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    //通用的IOC容器
    private Map<String, BeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>();

    private String configLocations;

    private BeanDefinitionReader reader;

    public DefaultApplicationContext(String configLocations) {
        this.configLocations = configLocations;
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refresh() throws Exception {

        //1、定位，定位配置文件
        reader = new BeanDefinitionReader(this.configLocations);

        //2、加载配置文件，扫描相关的类，把它们封装成BeanDefinition
        List<BeanDefinition> beanDefinitions = reader.loadBeanDefinitions();

        //3、注册，把配置信息放到容器里面(伪IOC容器)
        doRegisterBeanDefinition(beanDefinitions);

        //4、把不是延时加载的类，有提前初始化
        doAutowrited();
    }

    //只处理非延时加载的情况
    private void doAutowrited() {
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            if (!beanDefinitionEntry.getValue().isLazyInit()) {
                try {
                    getBean(beanName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void doRegisterBeanDefinition(List<BeanDefinition> beanDefinitions) throws Exception {

        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())) {
                throw new Exception("The “" + beanDefinition.getFactoryBeanName() + "” is exists!!");
            }
            beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
        }
        //到这里为止，容器初始化完毕
    }

    @Override
    public Object getBean(String beanName) throws Exception {
        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        Object instance = null;

        //这个逻辑还不严谨，自己可以去参考Spring源码
        //工厂模式 + 策略模式
        BeanPostProcessor postProcessor = new BeanPostProcessor();

        postProcessor.postProcessBeforeInitialization(instance, beanName);

        //先初始化Bean
        instance = instantiateBean(beanName, beanDefinition);

        //把这个对象封装到BeanWrapper中
        BeanWrapper beanWrapper = new BeanWrapper(instance);

        //拿到BeanWraoper之后，把BeanWrapper保存到IOC容器中去
        this.factoryBeanInstanceCache.put(beanName, beanWrapper);

        postProcessor.postProcessAfterInitialization(instance, beanName);

//        //3、注入
        populateBean(beanName, new BeanDefinition(), beanWrapper);


        return this.factoryBeanInstanceCache.get(beanName).getWrappedInstance();
    }

    private void populateBean(String beanName, BeanDefinition beanDefinition, BeanWrapper gpBeanWrapper) {
        Object instance = gpBeanWrapper.getWrappedInstance();

        Class<?> clazz = gpBeanWrapper.getWrappedClass();
        //判断只有加了注解的类，才执行依赖注入
        if (!(clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(Service.class))) {
            return;
        }

        //获得所有的fields
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Autowired.class)) {
                continue;
            }

            Autowired autowired = field.getAnnotation(Autowired.class);
            String autowiredBeanName = autowired.value().trim();
            if ("".equals(autowiredBeanName)) {
                autowiredBeanName = field.getType().getName();
            }

            //强制访问
            field.setAccessible(true);

            try {
                if (this.factoryBeanInstanceCache.get(autowiredBeanName) == null) {
                    continue;
                }
                field.set(instance, this.factoryBeanInstanceCache.get(autowiredBeanName).getWrappedInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private Object instantiateBean(String beanName, BeanDefinition gpBeanDefinition) {
        //1、拿到要实例化的对象的类名
        String className = gpBeanDefinition.getBeanClassName();

        //2、反射实例化，得到一个对象
        Object instance = null;
        try {
            if (this.factoryBeanObjectCache.containsKey(className)) {
                instance = this.factoryBeanObjectCache.get(className);
            } else {
                Class<?> clazz = Class.forName(className);
                instance = clazz.newInstance();
                this.factoryBeanObjectCache.put(className, instance);
                this.factoryBeanObjectCache.put(gpBeanDefinition.getFactoryBeanName(), instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return instance;
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws Exception {
        return (T) getBean(requiredType.getName());
    }

    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new  String[this.beanDefinitionMap.size()]);
    }

    public Properties getConfig(){
        return this.reader.getConfig();
    }
}
