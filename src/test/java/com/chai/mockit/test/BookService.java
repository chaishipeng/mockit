package com.chai.mockit.test;

import com.chai.mockit.client.annotation.MockIt;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public class BookService {

    @MockIt
    public String getBookName(String writer){
        return "_" + writer + "_Book";
    }

}
