package com.chai.mockit.test;

import com.chai.mockit.server.annotation.MockItServer;

import java.util.List;

/**
 * Created by chaishipeng on 2017/4/18.
 */
@MockItServer(className = "com.chai.mockit.test.BookService")
public class SpringServerHandler {

    public Object getBookName(List params) {
        return "tdadadasda";
    }

}
