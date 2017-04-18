package com.chai.mockit.common.serialize;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public interface Serialize {

    String serialize(Object obj);

    <T> T deSerialize(String content, Class<T> t);

}
