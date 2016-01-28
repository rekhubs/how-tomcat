package com.zl.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * Created by rek on 15/12/18.
 */
public class ServerSockEg {
    public static void main(String[] args) {
        try {
            InetAddress add =  java.net.InetAddress.getByName("127.0.0.1");
            ServerSocket ss = new ServerSocket(8080, 1, add);
//            ss.
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
