package uz.pdp;

import uz.pdp.service.UserService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;


public class Main {

    static Scanner scanInt = new Scanner(System.in);
    static Scanner scanStr = new Scanner(System.in);

    static UserService userService = new UserService();


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Utils.addMillion();
        while(true) {
            System.out.println("1. Sign in\t2. Sign up");
            String action = scanStr.nextLine();
            switch(action) {
                case "1" -> {
                    signIn();
                }
                case "2" -> {
                        signUp();
                }
            }
        }
    }

    private static void signUp() {

        System.out.print("name: ");
        String name = scanStr.nextLine();

        System.out.print("username: ");
        String username = scanStr.nextLine();

        System.out.print("password: ");
        String password = scanStr.nextLine();

        System.out.println(userService.addUser(new User(name, username, password)));
    }

    private static void signIn() {
        System.out.print("username: ");
        String username = scanStr.nextLine();

        System.out.print("password: ");
        String password = scanStr.nextLine();
        User user = userService.singIn(username, password);

        if (user == null) {
            System.out.println("wrong password or username\n");
        }
    }

    private static void connectionToDBIntro() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/sololearn",
                "postgres",
                "8463"
        );

        String schema = connection.getSchema();
        Statement statement = connection.createStatement();
//        statement.execute("insert into users(username, password) values('qwer', 'qwer');");

        ResultSet resultSet = statement.executeQuery("select * from users;");
        while(resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getObject("id", UUID.class));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            System.out.println(user);
        }
        // Big O (Olma) notation O(n)

        // Linear search, binary search O(log2(n))
    }

}