package ru.geekbrains.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp1 {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8888);
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            Scanner in = new Scanner(System.in);
            String login = null;
            String message = null;

            Thread readThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String message = inputStream.readUTF();
                            System.out.println(message);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            Thread writeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String login = in.nextLine();
                            outputStream.writeUTF(login);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            readThread.start();
            writeThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

