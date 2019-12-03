package com.lqb.spring.v3.context;

public interface AnnotationConfigRegistry {
    void register(Class<?>... annotatedClasses);
    void scan(String basePackage);
}
