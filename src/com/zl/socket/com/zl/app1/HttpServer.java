package com.zl.socket.com.zl.app1;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by rek on 16/1/29.
 */
public class HttpServer {

    public static final String WEB_ROOT = System.getProperty("user.dir") +
            File.separator + "webroot";

    // shutdown cmd
    private static final String SHUTDOWN_CMD = "/SHUTDOWN";
    private boolean shutdown = false;

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.await();

    }

    private void await() {
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // loop waiting for a request
        while (!shutdown) {
            Socket socket = null;
            InputStream input = null;
            OutputStream output = null;
            try {
                socket = serverSocket.accept();
                input = socket.getInputStream();
                output = socket.getOutputStream();

                // request & parse
                Request request = new Request(input);
                request.parse();

                // create response
                Response response = new Response(output);
                response.setRequest(request);
                response.sendStaticResource();

                // close socket
                socket.close();
                shutdown = request.getUri().equals(SHUTDOWN_CMD);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

}
