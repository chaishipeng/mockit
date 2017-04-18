package com.chai.mockit.client.spring;

import com.chai.mockit.client.common.Configs;
import com.chai.mockit.client.test.TestService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public class ConsoleHandler implements ApplicationContextAware{

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void on(){
        Configs.isMock = true;
        System.out.println("Mock start");
    }

    public void off(){
        Configs.isMock = false;
        System.out.println("Mock end");
    }

    public String show(){
        return new Boolean(Configs.isMock).toString();
    }

    public void test(){
        TestService textService = (TestService) applicationContext.getBean("testService");
        System.out.println(textService.test("chai"));
    }

}
