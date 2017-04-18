package com.chai.mockit.client.spring;

import com.chai.mockit.client.MockItFactory;
import com.chai.mockit.client.annotation.MockIt;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public class ClientBeanPost implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        if (isMockBean(o.getClass())){
            return getProxy(o);
        }
        return o;
    }

    private boolean isMockBean(Class class0){
        Method methods[] = class0.getMethods();
        for (Method method : methods) {
            MockIt mockIt = method.getAnnotation(MockIt.class);
            if (mockIt != null){
                return true;
            }
        }
        return false;
    }

    private Object getProxy(Object o){
        return MockItFactory.mock(o);
    }
}
