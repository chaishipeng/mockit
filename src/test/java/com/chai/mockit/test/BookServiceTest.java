package com.chai.mockit.test;

import com.chai.mockit.client.MockItFactory;
import com.chai.mockit.client.common.Configs;
import com.chai.mockit.client.test.TestService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public class BookServiceTest {

    public static void main(String[] args)throws Exception {
        /*BookService bookService = new BookService();
        Configs.isMock = true;
        BookService mockService = MockItFactory.mock(bookService);
        System.out.println(mockService.getBookName("chai"));*/

        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/appcontext-client.xml");

        Thread.currentThread().sleep(10000000);
    }

}
