package com.fmi.master;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {

            System.out.println("Server started");

            Socket socket = serverSocket.accept();
            System.out.println("Waiting for established");

            InputStream request = socket.getInputStream();
            OutputStream response = socket.getOutputStream();
        }
    }
}