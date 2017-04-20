package com.chai.mockit.server.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public class DefaultServerController implements ServerController {

    private Map<String, ServerHandler> handlers = new HashMap<String, ServerHandler>();

    public ServerController registerHandler(String className, ServerHandler handler){
        handlers.put(className, handler);
        return this;
    }

    public String handler(String className,String methodName, List<Map<String,Object>> params){
        ServerHandler serverHandler = handlers.get(className);
        return serverHandler.handler(methodName, params);
    }

}
