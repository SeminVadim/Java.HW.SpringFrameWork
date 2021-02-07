package ru.geekbrains.client;

import ru.geekbrains.server.ServerChat;
import ru.geekbrains.server.User;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientChat {
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private String name;

    private ServerChat serverChat;

    public ClientChat(ServerChat serverChat, Socket socket) {
        try {
            this.serverChat = serverChat;
            this.socket = socket;
            this.inputStream = new DataInputStream(socket.getInputStream());
            this.outputStream = new DataOutputStream(socket.getOutputStream());
            this.name = name;
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getName() {
        return name;
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket.setSoTimeout(120000);
                    try {
                        System.out.println("--- start login");
                        login();
                    } catch (InterruptedIOException e) {
                        System.out.println("--- catch ex");
                        logout();
                    }
                    socket.setSoTimeout(0);

                    printHistory();

                    readMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    logout();
                }
            }
        }).start();
    }

    public void sendMessage(String message) {
        try {
            outputStream.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMessage() throws IOException {
        System.out.print("--- star reading message from client: ");
        while (true) {
            String message = inputStream.readUTF();
            String messageFormat = String.format("Incoming  message from Client< %s >: %s", name, message);
            System.out.println(messageFormat);

            if (message.startsWith("/w")) {
                String[] personalString = message.split("\\s");
                String personalUser = personalString [1];
                messageFormat = "Incoming  message from Client < " + name +" >: ";
                for (int i = 2; i < personalString.length; i++) {
                    messageFormat = messageFormat + " " + personalString[i];
                }
                serverChat.personalMessage(personalUser, messageFormat);
            } else {
                serverChat.broadcastMessage(messageFormat);
            }
            if (message.equalsIgnoreCase("-exit")) {
                return;
            }
        }
    }

    public void logout() {
        serverChat.unsubscription(this);
        serverChat.broadcastMessage(String.format("Client < %s > is left", name));
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login() throws IOException {
        System.out.println("Client begin login on the Server Chat");
        sendMessage("please enter -login <user> <passwd>");
        while (true) {
            String loginInfo = inputStream.readUTF();
            System.out.println("in.Read = " + loginInfo);
            if (loginInfo.startsWith("-login")) {
                String[] splitLoginInfo = loginInfo.split("\\s");

                User areYouUser = serverChat.getLoginService().findUser(splitLoginInfo[1], splitLoginInfo[2]);

                System.out.println("areYouUser = " + areYouUser);
                if (areYouUser == null) {
                    System.out.println("incorrect login or passwd");
                    sendMessage("incorrect login or passwd");
                } else {
                    if (serverChat.checkLogin(areYouUser.getName())) {
                        System.out.println(String.format("User already login < %s >", areYouUser.getName()));
                        sendMessage(String.format("User already login < %s >", areYouUser.getName()));
                    } else {
                        System.out.println(String.format("User < %s >  login successful", areYouUser.getName()));
                        sendMessage("login successful");
                        name = areYouUser.getName();
                        serverChat.broadcastMessage(String.format("User <%s> is come in chat", name));
                        serverChat.subscription(this);

                        return;
                    }
                }
            } else {
                System.out.println("incorrect login or passwd");
                sendMessage("incorrect login or passwd");
            }
        }
    }


    public List<String> readHistory(int lines) {
        File fileHistory = new File("src/ru/geekbrains/HomeWork_07_Chat/history.txt");
        try (FileReader fileReader = new FileReader(fileHistory)) {
            BufferedReader br = new BufferedReader(fileReader);
            String str = null;
            List<String> listString = new ArrayList<>();
            while ((str = br.readLine()) != null) {
                listString.add(str);
            }

            List<String> listStringLast = new ArrayList<>();
            for (int i = listString.size() - lines; i <listString.size() ; i++) {
                listStringLast.add(listString.get(i));
            }
            return listStringLast;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void printHistory () {
        sendMessage("---History ---");
        List<String> history = readHistory(5);
        for (int i = 0; i < history.size(); i++) {
            sendMessage(history.get(i));
        }
        sendMessage("---History ---");
    }

}




