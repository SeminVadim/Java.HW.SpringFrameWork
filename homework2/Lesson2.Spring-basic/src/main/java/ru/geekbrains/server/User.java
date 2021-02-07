package ru.geekbrains.server;

public class User {
    private String login;
    private String passwd;
    private String name;

    public User(String login, String passwd, String name) {
        this.login = login;
        this.passwd = passwd;
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getName() {
        return name;
    }
}
