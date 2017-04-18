package com.chai.mockit.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public class SpringTest {

    public static void main(String[] args) throws IOException {

        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/appcontext.xml");
        context.getBean("server");

        System.in.read();

    }

}
