package com.luxoft.dao.jdbc;
import com.luxoft.dao.ClientDao;
import com.luxoft.entity.Client;
import org.apache.commons.codec.digest.DigestUtils;
import java.sql.*;
import java.util.Objects;
import java.util.UUID;
public class JdbcUserDao implements ClientDao {
    private static final String ADD_USER = """
            INSERT INTO users (name, email, password, sole)
            VALUES(?, ?, ?, ?)
            """;
    @Override
    public void addUser(Client client) {
        String sole = UUID.randomUUID().toString();
        System.out.println(client.getPassword());
        client.setPassword(DigestUtils.md5Hex(sole + client.getPassword()));
        System.out.println(client.getPassword());
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER)) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getEmail());
            preparedStatement.setString(3, client.getPassword());
            preparedStatement.setString(4, sole);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    @Override
    public boolean isUserExists(Client Client) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT password, sole FROM users WHERE email = ?")) {
            preparedStatement.setString(1, Client.getEmail());
            try(ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    String sole = resultSet.getString("sole");
                    String hashedPassword = DigestUtils.md5Hex(sole + Client.getPassword());
                    if (Objects.equals(hashedPassword, resultSet.getString("password"))) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return false;
    }
    @Override
    public boolean isMailAlreadyExist(String email) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT email FROM users")){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                if(Objects.equals(email, resultSet.getString("email"))){
                    return true;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        return false;
    }
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/Online-shop", "postgres", "pass");
    }
}