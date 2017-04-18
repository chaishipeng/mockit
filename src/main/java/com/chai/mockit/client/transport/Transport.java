package com.chai.mockit.client.transport;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public interface Transport {

    Map<String, String> call(Map<String,String> sendMap);

}
