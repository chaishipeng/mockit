package com.chai.mockit.common.serialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public class JsonSerialize implements Serialize {

    private Gson gson = new GsonBuilder().create();

    public String serialize(Object obj) {
        return gson.toJson(obj);
    }

    public <T> T deSerialize(String bytes, Class<T> t) {
        return gson.fromJson(bytes, t);
    }
}
