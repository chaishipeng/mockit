package com.chai.mockit.client.test;

import com.chai.mockit.client.annotation.MockIt;

/**
 * Created by chaishipeng on 2017/4/20.
 */
public class TestServiceImpl implements TestService {

    @MockIt
    public String test(String params){
        return "NotMock";
    }

}
