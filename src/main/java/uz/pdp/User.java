package uz.pdp;


import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private UUID id;
    private String name;
    private String username;
    private String password;

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public static User map(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getObject("id", UUID.class));
        user.setUsername(resultSet.getString("username"));
        user.setName(resultSet.getString("name"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }
}
