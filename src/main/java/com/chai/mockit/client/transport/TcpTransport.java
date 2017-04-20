package com.chai.mockit.client.transport;

import com.chai.mockit.client.common.Configs;
import com.chai.mockit.common.parsepack.PackParse;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.Map;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public class TcpTransport extends PackParse implements Transport {

    public Map<String, String> call(Map<String, String> sendMap) {

        try {
            return transportOnSocket(sendMap);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<String, String> transportOnSocket(Map<String, String> sendMap) throws IOException, ClassNotFoundException {
        /*byte[] sendBytes = ObjectAndByte.toByteArray(sendMap);
        byte[] recvBytes = callOnSocket(sendBytes);
        return (Map<String, String>) ObjectAndByte.toObject(recvBytes);*/
        Socket socket = new Socket(Configs.mockHost,Configs.mockPort);
        super.writePack(socket.getOutputStream(), sendMap);
        Map<String, String> recvMap = (Map<String, String>) super.readPack(socket.getInputStream());
        socket.close();
        return recvMap;

    }

   /* private byte[] callOnSocket(byte[] sendBytes) throws IOException {
        Socket socket = new Socket(Configs.mockHost,Configs.mockPort);
        super.writePack(socket.getOutputStream(), sendBytes);
        byte[] recvBytes = super.readPack(socket.getInputStream());
        socket.close();

        return recvBytes;
    }*/



}
