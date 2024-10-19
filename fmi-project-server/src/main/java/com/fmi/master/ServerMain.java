package com.fmi.master;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);

        System.out.println("Server started");

        Socket socket = serverSocket.accept();
        System.out.println("Waiting for connection");

        InputStream request = socket.getInputStream();
        OutputStream response = socket.getOutputStream();

        response.close();
        request.close();
        socket.close();
        serverSocket.close();
    }
}