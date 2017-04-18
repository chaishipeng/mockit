package com.chai.mockit.client.transport;

import com.chai.mockit.client.common.Configs;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.Map;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public class TcpTransport implements Transport {

    public Map<String, String> call(Map<String, String> sendMap) {

        try {
            return transportOnSocket(sendMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<String, String> transportOnSocket(Map<String, String> sendMap) throws IOException {
        byte[] sendBytes = ObjectAndByte.toByteArray(sendMap);
        byte[] recvBytes = callOnSocket(sendBytes);
        return (Map<String, String>) ObjectAndByte.toObject(recvBytes);
    }

    private byte[] callOnSocket(byte[] sendBytes) throws IOException {
        Socket socket = new Socket(Configs.mockHost,Configs.mockPort);
        int length = sendBytes.length;
        socket.getOutputStream().write(length);
        socket.getOutputStream().write(sendBytes);
        socket.getOutputStream().flush();

        int recvLength = socket.getInputStream().read();
        byte[] recvBytes = new byte[recvLength];
        socket.getInputStream().read(recvBytes);
        socket.close();

        return recvBytes;
    }



}
