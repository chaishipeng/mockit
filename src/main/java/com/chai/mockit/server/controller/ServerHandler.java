package com.chai.mockit.server.controller;

import java.util.List;
import java.util.Map;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public interface ServerHandler {

    Object handler(String methodName,List<Map<String,Object>> params);

}
