package com.chai.mockit.client.handler;

import com.chai.mockit.client.annotation.MockIt;
import com.chai.mockit.client.common.Configs;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chaishipeng on 2017/4/20.
 */
public class JdkInvocationHandler<T> implements InvocationHandler {

    private T target;

    private Class classt;

    private List<MethodWrapper> methodList = new ArrayList<MethodWrapper>();

    public JdkInvocationHandler(Class<T> tClass, T target){
        this.target = target;
        this.classt = tClass;
        init(tClass);
    }

    private void init(Class<T> tClass){
        Method[] methods = tClass.getMethods();
        for (Method method : methods){
            MockIt mockIt = method.getAnnotation(MockIt.class);
            if (mockIt != null){
                methodList.add(new MethodWrapper(method));
            }
        }
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (Configs.isMock && methodList.contains(new MethodWrapper(method))){
            return MockController.getObjInMock(method, args, classt);
        }

        return method.invoke(target, args);
    }

    private class MethodWrapper {

        private String name;

        private Class[] classs;

        MethodWrapper(Method method){
            name = method.getName();
            classs = method.getParameterTypes();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MethodWrapper that = (MethodWrapper) o;

            if (name != null ? !name.equals(that.name) : that.name != null) return false;
            // Probably incorrect - comparing Object[] arrays with Arrays.equals
            return arraysEqual(that.classs);

        }

        private boolean arraysEqual(Class[] tclasss){
            int length = classs == null ? 0 : classs.length;
            int thatlength = tclasss == null ? 0 : tclasss.length;
            if (length != thatlength){
                return false;
            }
            if (length == 0){
                return true;
            }
            for (int index = 0 ;index < classs.length ; index ++){
                Class currentClass = classs[index];
                Class thatClass = tclasss[index];
                if (currentClass.getName().equals(thatClass.getName())){
                    continue;
                } else {
                    return false;
                }
            }
            return true;
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + Arrays.hashCode(classs);
            return result;
        }
    }
}
