package com.chai.mockit.client.spring;

import com.chai.mockit.client.common.Configs;
import com.chai.mockit.client.test.TestService;
import com.chai.simconsole.api.ConsoleHandler;
import com.chai.simconsole.impl.CommandConsole;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.PrintStream;

/**
 * Created by chaishipeng on 2017/4/19.
 */
public class MockItCommandConsole extends CommandConsole implements ConsoleHandler, ApplicationContextAware{

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void start(){
        super.setPrefix("MockIt:");
        super.addConsoleHandler(this);
        super.setPort(0);
        super.start();
    }

    public String getHelp() {
        return "help";
    }

    public void _on(String args, PrintStream ps){
        Configs.isMock = true;
        ps.println("Mock Open");
    }

    public void _off(String args, PrintStream ps){
        Configs.isMock = false;
        ps.println("Mock off");
    }

    public void _show(String args, PrintStream ps){
        ps.println("IsMock:" + Configs.isMock);
        ps.println("MockServer:" + Configs.mockHost + "-" + Configs.mockPort);
    }

    public void _test(String args, PrintStream ps){
        TestService testService = applicationContext.getBean(TestService.class);
        ps.println(testService.test(args));
    }

    public void _host(String args, PrintStream ps){
        Configs.mockHost = args;
    }

    public void _port(String args, PrintStream ps){
        Configs.mockPort = Integer.parseInt(args);
    }
}
