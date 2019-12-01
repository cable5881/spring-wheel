package com.lqb.spring.v2.beans;

public interface BeanWrapper {

    /**
     * Return the bean instance wrapped by this object.
     */
    Object getWrappedInstance();

    /**
     * Return the type of the wrapped bean instance.
     */
    Class<?> getWrappedClass();

}
