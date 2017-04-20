package com.chai.mockit.server.controller;

import com.chai.mockit.common.serialize.Serialize;
import com.chai.mockit.common.utils.MockServiceLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public class ReflectServerHandler implements ServerHandler {

    private Object handler;

    private Serialize serialize = MockServiceLoader.loadService(Serialize.class);

    public ReflectServerHandler(Object handler){
        this.handler = handler;
    }

    public String handler(String methodName, List<Map<String, Object>> params) {
        try {
            Method method = handler.getClass().getMethod(methodName, List.class);
            if (method == null) {
                throw new RuntimeException(methodName + " not exits or Params not is List");
            }
            try {
                Object result = method.invoke(handler, params);
                return format(result);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(methodName + " not exits or Params not is List");
        }
        return null;
    }

    private String format(Object obj) {
        if (obj == null){
            return "";
        }
        if (obj.getClass().isPrimitive() || obj.getClass() == String.class){
            return obj.toString();
        }
        return serialize.serialize(obj);
    }
}
