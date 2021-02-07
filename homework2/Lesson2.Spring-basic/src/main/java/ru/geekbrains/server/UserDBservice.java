package ru.geekbrains.server;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDBservice implements Service<UserDB> {


    @Override
    public Collection<UserDB> findAll() {
        Connection connection = null;
        try {
            connection = ConnectionFactory.getInstance();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM mychat.user_data");
            List<UserDB> userDBS = new ArrayList<>();
            while (resultSet.next()) {
                UserDB userDB = new UserDB(
                        resultSet.getInt("user_id"),
                        resultSet.getString("user_name"),
                        resultSet.getString("user_login"),
                        resultSet.getString("user_passwd"),
                        resultSet.getInt("user_type")
                );
                userDBS.add(userDB);
            }
            return userDBS;
        } catch (SQLException throwables) {
            throw new RuntimeException("Something went wrong during DB-query", throwables);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    @Override
    public UserDB findById(int user_login) {
        Connection connection = null;
        try {
            connection = ConnectionFactory.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM mychat.user_data WHERE user_login = ?");
            preparedStatement.setInt(1,user_login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                UserDB userDB = new UserDB(
                resultSet.getInt("user_id"),
                resultSet.getString("user_login"),
                resultSet.getString("user_name"),
                resultSet.getString("user_passwd"),
                resultSet.getInt("user_type")
                );

                return userDB;
            }
            return null;

        } catch (SQLException throwables) {
            throw new RuntimeException("Something went wrong during DB-query", throwables);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

    }

    @Override
    public UserDB findByLoginPasswd(String user_login, String user_passwd) {
        Connection connection = null;
        try {
            connection = ConnectionFactory.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM mychat.user_data WHERE (user_login = ?) AND (user_passwd = ?)");
            preparedStatement.setString(1,user_login);
            preparedStatement.setString(2,user_passwd);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                UserDB userDB = new UserDB(
                        resultSet.getInt("user_id"),
                        resultSet.getString("user_login"),
                        resultSet.getString("user_name"),
                        resultSet.getString("user_passwd"),
                        resultSet.getInt("user_type")
                );

                return userDB;
            }
            return null;

        } catch (SQLException throwables) {
            throw new RuntimeException("Something went wrong during DB-query", throwables);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

    }

    @Override
    public void update(UserDB obj) {
        Connection connection = null;
        try {
            connection = ConnectionFactory.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE mychat.user_data SET " +
                    "user_login = ?," +
                    "user_name = ?, " +
                    "user_passwd = ?," +
                    "user_type = ?" +
                    " WHERE user_id = ?");
            preparedStatement.setString(1, obj.getUser_login());
            preparedStatement.setString(2, obj.getUser_name());
            preparedStatement.setString(3, obj.getUser_passwd());
            preparedStatement.setInt(4, obj.getUser_type());
            preparedStatement.setInt(5, obj.getUser_id());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new RuntimeException("Something went wrong during DB-query", throwables);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

    }

    @Override
    public boolean add(UserDB obj) {
        Connection connection = null;
        try {
            connection = ConnectionFactory.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO mychat.user_data " +
                    "(user_login, user_name, user_passwd,user_type)" +
                    "VALUES (?,?,?,?)");
            preparedStatement.setString(1, obj.getUser_name());
            preparedStatement.setString(2, obj.getUser_login());
            preparedStatement.setString(3, obj.getUser_passwd());
            preparedStatement.setInt(4, obj.getUser_type());
            return preparedStatement.execute();

        } catch (SQLException throwables) {
            throw new RuntimeException("Something went wrong during DB-query", throwables);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

}
