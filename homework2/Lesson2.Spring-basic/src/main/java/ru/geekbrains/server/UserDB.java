package ru.geekbrains.server;

public class UserDB {
        private int user_id;
        private String user_login;
        private String user_name;
        private String user_passwd;
        private int user_type;


        public UserDB(int user_id, String user_login, String user_name, String user_passwd, int user_type) {
            this.user_id = user_id;
            this.user_login = user_login;
            this.user_name = user_name;
            this.user_passwd = user_passwd;
            this.user_type = user_type;
        }

        @Override
        public String toString() {
            return "UserDB{" +
                    "user_id=" + user_id +
                    ", user_login='" + user_login + '\'' +
                    ", user_name='" + user_name + '\'' +
                    ", user_passwd='" + user_passwd + '\'' +
                    ", user_type=" + user_type +
                    '}';
        }

        public int getUser_id() {
            return user_id;
        }

        public String getUser_login() {
            return user_login;
        }

        public String getUser_name() {
            return user_name;
        }

        public String getUser_passwd() {
            return user_passwd;
        }

        public int getUser_type() {
            return user_type;
        }
    }


