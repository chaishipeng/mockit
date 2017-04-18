package com.chai.mockit.client;

import com.chai.mockit.client.handler.MockItMethodInterceptor;
import net.sf.cglib.proxy.Enhancer;
import org.springframework.aop.support.AopUtils;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public class MockItFactory {

    public static <T> T mock(T t){
        Class<T> tClass = getClass(t);
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

}
