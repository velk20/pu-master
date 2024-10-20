package com.fmi.master;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpServer {

    private static final String NEW_LINE = "\r\n";

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(1423);
        System.out.println("Server is listening on port: " + serverSocket.getLocalPort());

        while (serverSocket.isBound()) {
            Socket socket = serverSocket.accept();

            InputStream request = socket.getInputStream();
            OutputStream response = socket.getOutputStream();

            Thread.sleep(500);

            String message = buildHTTPResponse("Hello, World!");
            response.write(message.getBytes(StandardCharsets.UTF_8));

            response.close();
            request.close();
            socket.close();
        }
    }

    private static String buildHTTPResponse(String body) {
        // Define the status line
        String statusLine = "HTTP/1.1 200 OK" + NEW_LINE;

        // Define the headers
        String headers =
                "Content-Type: text/plain, text/html" + NEW_LINE +
                        "Content-Length: " + body.getBytes(StandardCharsets.UTF_8).length + NEW_LINE + // Length of the body content
                        "Access-Control-Allow-Origin: *" + NEW_LINE +   // Allow all origins (or specify a domain)
                        "Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS" + NEW_LINE + // Allowed methods
                        "Access-Control-Allow-Headers: Content-Type" + NEW_LINE + NEW_LINE;  // Allowed headers

        // Combine status line, headers, and body
        return statusLine + headers + body;
    }
}