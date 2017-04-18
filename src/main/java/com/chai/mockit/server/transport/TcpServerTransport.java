package com.chai.mockit.server.transport;

import com.chai.mockit.client.transport.ObjectAndByte;
import com.chai.mockit.common.Constants;
import com.chai.mockit.common.serialize.Serialize;
import com.chai.mockit.common.utils.MockServiceLoader;
import com.chai.mockit.server.controller.ServerController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public class TcpServerTransport implements ServerTransport {

    private Serialize serialize = MockServiceLoader.loadService(Serialize.class);

    private ServerController serverController = MockServiceLoader.loadService(ServerController.class);

    private int port;

    public void start() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                startServer();
            }
        });
        t.setDaemon(true);
        t.start();
    }

    private void startServer(){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            while(true){
                Socket socket = serverSocket.accept();
                handler(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handler(Socket socket)throws IOException{
        int length = socket.getInputStream().read();
        byte[] recvBytes = new byte[length];
        socket.getInputStream().read(recvBytes);
        Map<String,String> recvMap = (Map<String, String>) ObjectAndByte.toObject(recvBytes);
        String className = (String) recvMap.get(Constants.PACK_CLASS_KEY);
        String methodName = (String) recvMap.get(Constants.PACK_METHOD_KEY);
        String paramStr = (String) recvMap.get(Constants.PACK_PARAMS_KEY);
        List<Map<String,Object>> params = serialize.deSerialize(paramStr, List.class);

        Object result = serverController.handler(className, methodName, params);

        String res = serialize.serialize(result);
        recvMap.put(Constants.PACK_RETURN_KEY, res);

        byte[] sendBytes = ObjectAndByte.toByteArray(recvMap);
        int sendlength = sendBytes.length;

        socket.getOutputStream().write(sendlength);
        socket.getOutputStream().write(sendBytes);
        socket.getOutputStream().flush();
    }

    public void setPort(int port) {
        this.port = port;
    }
}
