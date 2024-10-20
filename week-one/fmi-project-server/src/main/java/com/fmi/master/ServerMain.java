package com.fmi.master;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

    public static final int COUNT_BREAK = 10;
    public static final int EXIT_CODE = 1;
    public static final int COMMON_CODE = 9;
    public static final int INIT_ITERATION_COUNT = 0;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);

        System.out.println("Server started");

        Socket socket = serverSocket.accept();
        System.out.println("Waiting for connection");

        InputStream request = socket.getInputStream();
        OutputStream response = socket.getOutputStream();

        int iterationCount = INIT_ITERATION_COUNT;

        while (socket.isConnected()) {
            int responseToken = request.read();
            System.out.println("Response: " + responseToken);


            if (iterationCount == COUNT_BREAK) {
                response.write(EXIT_CODE);
            }
            response.write(COMMON_CODE);
            iterationCount++;

        }


        response.close();
        request.close();
        socket.close();
        serverSocket.close();
    }
}