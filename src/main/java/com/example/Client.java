package com.example;

import java.io.*;
import java.net.*;

public class Client {
    public static void sendMessage(String message) {
        try {
            Socket socket = new Socket("127.0.0.1", 8888);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            writer.write(message);
            writer.newLine();
            writer.flush();

            socket.close();
        } catch (IOException e) {
            // Удалено: e.printStackTrace();
        }
    }
}
