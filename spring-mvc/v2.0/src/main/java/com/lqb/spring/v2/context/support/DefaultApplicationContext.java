package com.lqb.spring.v2.context.support;

import com.lqb.spring.v2.beans.BeanDefinitionRegistry;
import com.lqb.spring.v2.context.AnnotatedBeanDefinitionReader;
import com.lqb.spring.v2.context.AnnotationConfigRegistry;
import com.lqb.spring.v2.context.ClassPathBeanDefinitionScanner;
import com.lqb.spring.v2.core.factory.ConfigurableListableBeanFactory;
import com.lqb.spring.v2.core.factory.support.DefaultListableBeanFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultApplicationContext extends AbstractApplicationContext implements BeanDefinitionRegistry, AnnotationConfigRegistry{

    //实际在AnnotationConfigServletWebServerApplicationContext中
    private final AnnotatedBeanDefinitionReader reader;

    //实际在AnnotationConfigServletWebServerApplicationContext中
    private final ClassPathBeanDefinitionScanner scanner;

    //实际在 GenericApplicationContext 中
    private final DefaultListableBeanFactory beanFactory;

    private String[] basePackages;

    private final List<Class<?>> annotatedClasses = new ArrayList<>();

    public DefaultApplicationContext() {
        this.beanFactory = new DefaultListableBeanFactory();
        this.reader = new AnnotatedBeanDefinitionReader(this);
        this.scanner = new ClassPathBeanDefinitionScanner(this);
    }

    @Override
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        if (this.basePackages != null && this.basePackages.length > 0) {
            this.scanner.scan(this.basePackages);
        }
        if (!this.annotatedClasses.isEmpty()) {
            this.reader.register(this.annotatedClasses);
        }
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() {
        return this.beanFactory;
    }


    @Override
    public void register(Class<?>... annotatedClasses) {
        this.annotatedClasses.addAll(Arrays.asList(annotatedClasses));
    }

    @Override
    public void scan(String... basePackages) {
        this.basePackages = basePackages;
    }
}
