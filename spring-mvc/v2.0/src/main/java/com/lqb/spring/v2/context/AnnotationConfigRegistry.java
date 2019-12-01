package com.lqb.spring.v2.context;

public interface AnnotationConfigRegistry {
    void register(Class<?>... annotatedClasses);
    void scan(String... basePackages);
}
