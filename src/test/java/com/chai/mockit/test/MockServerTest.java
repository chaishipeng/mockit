package com.chai.mockit.test;

import com.chai.mockit.server.database.SqliteDB;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public class MockServerTest {

    public static void main(String[] args) throws IOException, InterruptedException {

        SqliteDB.dbPath = "/Users/chaishipeng/lifeCode/mockit/src/test/java/com/chai/mockit/test/test.db";
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/appcontext.xml");

        Thread.currentThread().sleep(1000000);

    }

}
