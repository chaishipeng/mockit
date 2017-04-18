package com.chai.mockit.client.spring;

import com.chai.mockit.client.common.Configs;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public class ConsoleRead implements ApplicationContextAware {

    private ConsoleHandler consoleHandler = new ConsoleHandler();

    private ApplicationContext applicationContext;

    private int remotePort;

    private RemoteConsole remoteConsole;

    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        consoleHandler.setApplicationContext(applicationContext);
    }

    public void start(){
        openConsoleOnRemote();
        Thread t = new Thread(new Runnable() {
            public void run() {
                startConsole();
            }
        });
        t.setDaemon(true);
        t.start();
    }

    private void openConsoleOnRemote(){
        remoteConsole = new RemoteConsole(remotePort);
    }

    private void startConsole(){
        while(true){
            System.out.print("MockIt:");
            String line = remoteConsole.getScanner().nextLine();
            if (line == null || line.trim().length()<=0) {
                continue;
            }
            String[] datas = line.split(" ");
            String methodName = datas[0];
            String args = datas.length > 1 ? datas[1]:null;
            try {
                Method method = ConsoleHandler.class.getMethod(methodName, String.class);
                Object result = method.invoke(consoleHandler, args);
                if (result != null){
                    System.out.println(result);
                }
            } catch (Exception e) {

            }
            System.out.println();
        }
    }

}
