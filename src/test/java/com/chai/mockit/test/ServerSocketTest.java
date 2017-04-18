package com.chai.mockit.test;

import com.chai.mockit.server.transport.ServerTransport;
import com.chai.mockit.server.transport.TcpServerTransport;


/**
 * Created by chaishipeng on 2017/4/18.
 */
public class ServerSocketTest {

    public static void main(String[] args) {

        ServerTransport transport = new TcpServerTransport();
        transport.setPort(8086);
        transport.start();

    }

}
