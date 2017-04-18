package com.chai.mockit.client.spring;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by chaishipeng on 2017/4/18.
 */
public class RemoteConsole {

    private Scanner scanner = new Scanner(System.in);

    public RemoteConsole(final int port){
        if (port <=0 ){
            return;
        }
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    openConsole(port);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void openConsole(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        while(true){
            Socket socket = serverSocket.accept();
            scanner = new Scanner(socket.getInputStream());
            System.setOut(new PrintStream(socket.getOutputStream()));
        }
    }

    public Scanner getScanner(){
        return scanner;
    }

}
