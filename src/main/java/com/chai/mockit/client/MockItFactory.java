package com.chai.mockit.client;

import com.chai.mockit.client.handler.JdkInvocationHandler;
import com.chai.mockit.client.handler.MockItMethodInterceptor;
import net.sf.cglib.proxy.Enhancer;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public class MockItFactory {

    public static <T> T mock(T t, String type){
        Class<T> tClass = getClass(t);
        if ("JDK".equals(type)){
            return mockObjByJDK(t, tClass);
        }
        return mockObj(t, tClass);
    }

    private static <T> Class<T> getClass(T t) {
        boolean isAop = AopUtils.isAopProxy(t);
        if (isAop) {
            return (Class<T>)AopUtils.getTargetClass(t);
        }
        return (Class<T>) t.getClass();
    }

    private static <T> T mockObj(T t, Class<T> tclass){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(tclass);
        enhancer.setCallback(new MockItMethodInterceptor(tclass, t));
        return (T)enhancer.create();
    }

    private static <T> T mockObjByJDK(T t, Class<T> tclass){
        InvocationHandler handler = new JdkInvocationHandler(tclass, t);
        return (T) Proxy.newProxyInstance(tclass.getClassLoader(), tclass.getInterfaces(), handler);
    }

}
