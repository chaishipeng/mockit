package com.chai.mockit.server.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public interface ServerController {

    ServerController registerHandler(String className, ServerHandler handler);

    Object handler(String className,String methodName, List<Map<String,Object>> params);

}
