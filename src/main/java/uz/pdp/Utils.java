package uz.pdp;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Utils {
    private static Connection connection;

    @SneakyThrows
    public static Connection getConnection() {
        if (connection == null) {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/sololearn",
                    "postgres",
                    "8463"
            );
        }
        return connection;
    }

    public static void addMillion() {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        for(int j = 0;j < 100; j++) {
            executorService.execute(() -> {
                try {
                    for (int i = 0; i < 10000; i++) {
                        System.out.println("new data");
                        PreparedStatement preparedStatement = getConnection().prepareStatement("select * from add_user(?, ?, ?)");
                        preparedStatement.setString(1, UUID.randomUUID().toString());
                        preparedStatement.setString(2, UUID.randomUUID().toString());
                        preparedStatement.setString(3, UUID.randomUUID().toString());
                        preparedStatement.executeQuery();
                        preparedStatement.close();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}