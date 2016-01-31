package com.zl.socket.com.zl.app1;

import java.io.*;

/**
 * Created by rek on 16/1/31.
 */
public class Response {

//    HTTP Response = status-line * ((general-header | response-header | entity-header) CRLF) CRLF
//    [ message-body ]
//    status-line = HTTP-Version SP status-code SP reason phrase CRLF

    private static final int BUFFER_SIZE = 1024;
    Request request;
    OutputStream output;

    public Response(OutputStream output){
        this.output = output;
    }

    public void setRequest(Request request){
        this.request = request;
    }

    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            System.out.println("web root is: " + HttpServer.WEB_ROOT);
            File file = new File(HttpServer.WEB_ROOT, request.getUri());
            if (file.exists()) {
                String succHeader = "HTTP/1.1 200 good\r\n" +
                        "Content-Type: text/html\n";
                output.write(succHeader.getBytes());
                fis = new FileInputStream(file);
                int ch = fis.read(bytes, 0, this.BUFFER_SIZE);
                while (ch != -1) {
                    output.write(bytes, 0, ch);
                    ch = fis.read(bytes, 0, this.BUFFER_SIZE);
                }
            } else {
                // file not found
                String errorMsg = "HTTP/1.1 404 file-not-found\r\n" +
                        "Content-Type: text/html\r\n" +
//                        "content-length: 23\r\n" +
                        "<html> <h1>file not found</h1> </html>";
                output.write(errorMsg.getBytes());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null)
                fis.close();
        }
    }

}
