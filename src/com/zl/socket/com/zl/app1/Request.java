package com.zl.socket.com.zl.app1;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by rek on 16/1/31.
 */
public class Request {
    private InputStream input;
    private String uri;

    public Request(InputStream input){
        this.input = input;
    }

    public void parse(){
        StringBuffer request = new StringBuffer(2048);
        int i;
        byte[] buffer = new byte[2048];
        try {
            i = input.read(buffer);
        }catch (IOException e){
            e.printStackTrace();
            i = -1;
        }

        // e.g. GET /index.html HTTP/1.1
        for (int j = 0; j < i; j++) {
            request.append((char) buffer[j]);
        }
        System.out.println("===== getting the request: (" + System.currentTimeMillis() + ") =====");
        System.out.print(request.toString());
        uri = parseUri(request.toString());
    }

    public String parseUri(String requestString){
        int idx1, idx2;
        idx1 = requestString.indexOf(' ');
        if (idx1 != -1){
            idx2 = requestString.indexOf(' ', idx1 + 1);
            if (idx2 > idx1)
                return requestString.substring(idx1 + 1, idx2);
        }
        return null;
    }

    public String getUri(){
        return uri;
    }
}
