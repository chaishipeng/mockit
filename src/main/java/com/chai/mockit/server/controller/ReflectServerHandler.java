package com.chai.mockit.server.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public class ReflectServerHandler implements ServerHandler {

    private Object handler;

    public ReflectServerHandler(Object handler){
        this.handler = handler;
    }

    public Object handler(String methodName, List<Map<String, Object>> params) {
        try {
            Method method = handler.getClass().getMethod(methodName, List.class);
            if (method == null) {
                throw new RuntimeException(methodName + " not exits or Params not is List");
            }
            try {
                Object result = method.invoke(handler, params);
                return result;
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
}
