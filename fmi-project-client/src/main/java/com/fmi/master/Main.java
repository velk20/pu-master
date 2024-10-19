package com.fmi.master;

import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {

        try (Socket socket = new Socket("localhost", 5678)) {

        }
    }
}