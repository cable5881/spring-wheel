package com.lqb.spring.v3.beans;

public class BeanWrapperImpl implements BeanWrapper {

    private Object wrappedObject;

    public BeanWrapperImpl(Object wrappedObject) {
        this.wrappedObject = wrappedObject;
    }

    @Override
    public Object getWrappedInstance() {
        return this.wrappedObject;
    }

    @Override
    public Class<?> getWrappedClass() {
        return getWrappedInstance().getClass();
    }
}
