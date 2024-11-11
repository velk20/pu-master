package com.fmi.master;

import com.fmi.master.system.ApplicationLoader;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class FrameworkApplication {
    private static final String NEW_LINE = "\r\n";
    private static ApplicationLoader applicationLoader = new ApplicationLoader();

    public static void run(Class<?> mainClass) {
        try {
            bootstrap(mainClass);
            startWebServer();
        }catch (Exception e) {
            e.printStackTrace();
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



    private static void bootstrap(Class<?> mainClass) throws IOException, ClassNotFoundException {
            applicationLoader.findAllClasses(mainClass.getPackageName());
    }

    private static void startWebServer() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {


        //HTTP Server
        ServerSocket serverSocket = new ServerSocket(1423);
        System.out.println("Server is listening on port: " + serverSocket.getLocalPort());

        while (serverSocket.isBound()) {
            Socket socket = serverSocket.accept();

            InputStream request = socket.getInputStream();
            OutputStream response = socket.getOutputStream();

            InputStreamReader reader = new InputStreamReader(request, StandardCharsets.UTF_8);
            BufferedReader httpRequestReader = new BufferedReader(reader);

            String currentLine;

            String httpMethod = "";
            String httpEndpoint = "";


            while ((currentLine = httpRequestReader.readLine()) != null) {
                String[] httpHeaderTitleCollection = currentLine.split(" ");
                httpMethod = httpHeaderTitleCollection[0];
                httpEndpoint = httpHeaderTitleCollection[1];
                break;
            }

            String controllerMessage = applicationLoader.executeController(httpMethod, httpEndpoint);

            String message = buildHTTPResponse(controllerMessage);
            response.write(message.getBytes(StandardCharsets.UTF_8));

            response.close();
            request.close();
            socket.close();
        }
    }
}
