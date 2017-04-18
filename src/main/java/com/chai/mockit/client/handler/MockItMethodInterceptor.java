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

    private Transport transport = MockServiceLoader.loadService(Transport.class);

    private Serialize serialize = MockServiceLoader.loadService(Serialize.class);

    public MockItMethodInterceptor(Class<T> tClass, T target){
        this.target = target;
        this.classt = tClass;
        init(tClass);
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        if (Configs.isMock && methodList.contains(method)){
            return getObjInMock(method, objects);
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

    private Object getObjInMock(Method method, Object[] objects){
        Map<String, String> sendPack = new HashMap<String, String>();
        sendPack.put(Constants.PACK_CLASS_KEY, classt.getName());
        sendPack.put(Constants.PACK_METHOD_KEY, method.getName());
        sendPack.put(Constants.PACK_PARAMS_KEY, serialize.serialize(getParams(objects)));
        Map<String, String> recvMaps = transport.call(sendPack);
        String recvContent = recvMaps.get(Constants.PACK_RETURN_KEY);
        Object obj = serialize.deSerialize(recvContent, method.getReturnType());
        return obj;
    }

    private List<Map<String,Object>> getParams(Object[] objects){
        List<Map<String,Object>> params = new ArrayList<Map<String, Object>>();
        if (objects == null){
            return params;
        }
        int index = 0;
        for (Object obj : objects){
            Map<String,Object> param = new HashMap<String, Object>();
            param.put("Class", obj.getClass().getName());
            param.put("Data", obj);
            params.add(param);
        }
        return params;
    }
}
