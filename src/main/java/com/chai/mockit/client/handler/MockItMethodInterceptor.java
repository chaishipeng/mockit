package com.chai.mockit.client.handler;

import com.chai.mockit.client.annotation.MockIt;
import com.chai.mockit.client.common.Configs;
import com.chai.mockit.common.Constants;
import com.chai.mockit.common.serialize.Serialize;
import com.chai.mockit.client.transport.Transport;
import com.chai.mockit.common.utils.MockServiceLoader;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public class MockItMethodInterceptor<T> implements MethodInterceptor {

    private T target;

    private Class classt;

    private List<Method> methodList = new ArrayList<Method>();

    public MockItMethodInterceptor(Class<T> tClass, T target){
        this.target = target;
        this.classt = tClass;
        init(tClass);
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        if (Configs.isMock && methodList.contains(method)){
            return MockController.getObjInMock(method, objects, classt);
        }

        Object result = method.invoke(target, objects);

        return result;
    }

    private void init(Class<T> tClass){
        Method[] methods = tClass.getMethods();
        for (Method method : methods){
            MockIt mockIt = method.getAnnotation(MockIt.class);
            if (mockIt != null){
                methodList.add(method);
            }
        }
    }
}
