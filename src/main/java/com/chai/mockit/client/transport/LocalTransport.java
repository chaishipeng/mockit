package com.chai.mockit.client.transport;

import com.chai.mockit.common.Constants;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public class LocalTransport implements Transport {
    public Map<String, String> call(Map<String, String> sendMap) {
        sendMap.put(Constants.PACK_RETURN_KEY, "ChaiMock");
        return sendMap;
    }
}
