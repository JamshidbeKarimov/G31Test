package uz.pdp.service;

import lombok.SneakyThrows;
import uz.pdp.User;
import uz.pdp.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class UserService {
    private final Connection connection;
    PreparedStatement signInStatement;
    @SneakyThrows
    public UserService(){
        connection =  Utils.getConnection();
        signInStatement = connection.prepareStatement("select * from sign_in(?, ?);");

    }

    @SneakyThrows
    public String addUser(User user){
        Statement statement = connection.createStatement();
        String query = "select * from add_user('%s', '%s', '%s');";
        ResultSet resultSet = statement.executeQuery(
                String.format(query, user.getName(), user.getUsername(), user.getPassword())
        );
        resultSet.next();
        return resultSet.getString("add_user");
    }

    // this is just for test
    public void dbException() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("insert into users(username, password) values ('qwer', 'qwer')" );
    }

    @SneakyThrows
    public User singIn(String username, String password){
        Statement statement = connection.createStatement();
        String signIn = "select * from sign_in('%s', '%s');";
        ResultSet resultSet = statement.executeQuery(String.format(signIn, username, password));

        if (resultSet.next()) {
            return User.map(resultSet);
        }
        return null;
    }

    @SneakyThrows
    public User singIn2(String username, String password){
        signInStatement.setString(1, username);
        signInStatement.setString(2, password);
        ResultSet resultSet = signInStatement.executeQuery();
        if (resultSet.next()) {
            return User.map(resultSet);
        }
        return null;
    }
}
