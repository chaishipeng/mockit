package com.chai.mockit.client.handler;

import com.chai.mockit.client.annotation.MockIt;
import com.chai.mockit.client.transport.Transport;
import com.chai.mockit.common.Constants;
import com.chai.mockit.common.serialize.Serialize;
import com.chai.mockit.common.utils.MockServiceLoader;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chaishipeng on 2017/4/20.
 */
public class MockController {

    private static Transport transport = MockServiceLoader.loadService(Transport.class);

    private static Serialize serialize = MockServiceLoader.loadService(Serialize.class);

    public static Object getObjInMock(Method method, Object[] objects, Class classt){
        Map<String, String> sendPack = new HashMap<String, String>();
        sendPack.put(Constants.PACK_CLASS_KEY, classt.getName());
        sendPack.put(Constants.PACK_METHOD_KEY, method.getName());
        sendPack.put(Constants.PACK_PARAMS_KEY, serialize.serialize(getParams(objects)));
        Map<String, String> recvMaps = transport.call(sendPack);
        String recvContent = recvMaps.get(Constants.PACK_RETURN_KEY);
        Object obj = serialize.deSerialize(recvContent, method.getReturnType());
        return obj;
    }

    private static List<Map<String,Object>> getParams(Object[] objects){
        List<Map<String,Object>> params = new ArrayList<Map<String, Object>>();
        if (objects == null){
            return params;
        }
        int index = 0;
        for (Object obj : objects){
            if (obj == null){
                continue;
            }
            Map<String,Object> param = new HashMap<String, Object>();
            param.put("Class", obj.getClass().getName());
            param.put("Data", obj);
            params.add(param);
        }
        return params;
    }

}
