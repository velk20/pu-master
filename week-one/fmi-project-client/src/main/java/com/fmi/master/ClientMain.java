package com.fmi.master;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientMain {
    private static final int REQUEST_TOKEN = 8;
    public static final int EXIT_RESPONSE = 1;

    public static void main(String[] args) throws IOException, InterruptedException {

        Socket socket = new Socket("localhost", 1234);

        InputStream response = socket.getInputStream();
        OutputStream request = socket.getOutputStream();

        while (socket.isConnected()) {
            //send request to server
            request.write(REQUEST_TOKEN);

            int responseToken = response.read();

            //waiting for response from server
            System.out.println("Server responded with: " + responseToken);

            if (responseToken == EXIT_RESPONSE) {
                break;
            }

            Thread.sleep(1000);

        }

        request.close();
        response.close();
        socket.close();
    }
}