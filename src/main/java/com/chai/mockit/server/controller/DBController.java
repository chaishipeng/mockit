package com.chai.mockit.server.controller;

import com.chai.mockit.server.database.SqliteDB;

import java.util.List;
import java.util.Map;

/**
 * Created by chaishipeng on 2017/4/19.
 */
public class DBController implements ServerController{
    public ServerController registerHandler(String className, ServerHandler handler) {
        return null;
    }

    public String handler(String className, String methodName, List<Map<String, Object>> params) {
        SqliteDB.Result result =  SqliteDB.queryForList("select content from mockitdb where classname = '" + className + "' and methodname = '" + methodName + "'");
        List<Map<String, String>> res = result.getDatas();
        if (res!= null && res.size() >= 1){
            return res.get(0).get("content");
        }
        return "";
    }
}
