package com.chai.mockit.test;

import com.chai.mockit.client.MockItFactory;
import com.chai.mockit.client.common.Configs;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public class BookServiceTest {

    public static void main(String[] args) {
        BookService bookService = new BookService();
        Configs.isMock = true;
        BookService mockService = MockItFactory.mock(bookService);
        System.out.println(mockService.getBookName("chai"));
    }

}
