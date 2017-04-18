package com.chai.mockit.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public class MockServiceLoader {

    private static Map<String, Object> serviceCache = new HashMap<String, Object>();

    public static <T> T loadService(Class<T> tclass){
        if (serviceCache.containsKey(tclass.getName())){
            return (T) serviceCache.get(tclass.getName());
        }
        ServiceLoader<T> serviceLoader = ServiceLoader.load(tclass);
        for (T t0 : serviceLoader){
            serviceCache.put(tclass.getName(), t0);
            return t0;
        }
        return null;
    }

}
