package com.fmi.master;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientMain {
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 1234);
        InputStream response = socket.getInputStream();
        OutputStream request = socket.getOutputStream();

        request.close();
        response.close();
        socket.close();
    }
}