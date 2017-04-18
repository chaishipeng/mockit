package com.chai.mockit.server.spring;

import com.chai.mockit.common.utils.MockServiceLoader;
import com.chai.mockit.server.annotation.MockItServer;
import com.chai.mockit.server.controller.ReflectServerHandler;
import com.chai.mockit.server.controller.ServerController;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public class MockServerBeanPost implements BeanPostProcessor{
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        MockItServer mockItServer = o.getClass().getAnnotation(MockItServer.class);
        if (mockItServer != null){
            registerServer(mockItServer, o);
        }
        return o;
    }

    private void registerServer(MockItServer mockItServer, Object o){
        String className = mockItServer.className();
        ReflectServerHandler reflectServerHandler = new ReflectServerHandler(o);
        MockServiceLoader.loadService(ServerController.class).registerHandler(className, reflectServerHandler);
    }
}
