package com.lqb.spring.v3.context.support;

import com.lqb.spring.v3.beans.BeanDefinitionRegistry;
import com.lqb.spring.v3.beans.config.BeanDefinition;
import com.lqb.spring.v3.context.AnnotatedBeanDefinitionReader;
import com.lqb.spring.v3.context.AnnotationConfigRegistry;
import com.lqb.spring.v3.context.BeanDefinitionScanner;
import com.lqb.spring.v3.context.ConfigurableApplicationContext;
import com.lqb.spring.v3.core.factory.support.DefaultListableBeanFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class DefaultApplicationContext implements ConfigurableApplicationContext,
        BeanDefinitionRegistry, AnnotationConfigRegistry {

    private final AnnotatedBeanDefinitionReader reader;

    private final BeanDefinitionScanner scanner;

    private final DefaultListableBeanFactory beanFactory;

    /**目前只支持一个配置文件*/
    private String configLocation;

    private final List<Class<?>> annotatedClasses = new ArrayList<>();

    public DefaultApplicationContext(String configLocation) {
        this.beanFactory = new DefaultListableBeanFactory();
        this.reader = new AnnotatedBeanDefinitionReader(this);
        this.scanner = new BeanDefinitionScanner(this);
        this.configLocation = configLocation;
        refresh();
    }

    @Override
    public void refresh() {
        DefaultListableBeanFactory beanFactory = obtainFreshBeanFactory();

        scan(configLocation);


    }

    @Override
    public DefaultListableBeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    @Override
    public void register(Class<?>... annotatedClasses) {
        this.annotatedClasses.addAll(Arrays.asList(annotatedClasses));
    }

    @Override
    public void scan(String configLocation) {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream(configLocation));
            String basePackage = properties.getProperty("scanPackage");
            this.scanner.scan(basePackage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

    protected DefaultListableBeanFactory obtainFreshBeanFactory() {
        DefaultListableBeanFactory beanFactory = getBeanFactory();
        return beanFactory;
    }

    @Override
    public Object getBean(String name) {
        return getBeanFactory().getBean(name);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) {
        return getBeanFactory().getBean(requiredType);
    }
}
