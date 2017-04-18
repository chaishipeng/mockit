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

    public void on(String args){
        Configs.isMock = true;
        System.out.println("Mock start");
    }

    public void off(String args){
        Configs.isMock = false;
        System.out.println("Mock end");
    }

    public String show(String args){
        return new Boolean(Configs.isMock).toString();
    }

    public void test(String args){
        TestService textService = (TestService) applicationContext.getBean("testService");
        System.out.println(textService.test("chai"));
    }

    public void addr(String args){
        String hosts[] = args.split(":");
        Configs.mockHost = hosts[0];
        Configs.mockPort = Integer.parseInt(hosts[1]);
        System.out.println("MockServer-Host:" + Configs.mockHost);
        System.out.println("MockServer-Port:" + Configs.mockPort);
    }

}
