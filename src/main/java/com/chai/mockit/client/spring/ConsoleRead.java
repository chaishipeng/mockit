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

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        consoleHandler.setApplicationContext(applicationContext);
    }

    public void start(){
        Thread t = new Thread(new Runnable() {
            public void run() {
                startConsole();
            }
        });
        t.setDaemon(true);
        t.start();
    }

    private void startConsole(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.print("MockIt:");
            String line = scanner.nextLine();
            if (line == null || line.trim().length()<=0) {
                continue;
            }
            try {
                Method method = ConsoleHandler.class.getMethod(line, null);
                Object result = method.invoke(consoleHandler, null);
                if (result != null){
                    System.out.println(result);
                }
            } catch (Exception e) {

            }
            System.out.println();
        }
    }

}
