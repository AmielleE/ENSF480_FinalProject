package dao;

import java.sql.*;
import model.User;

public class user_dao {
    public int addUser(User user) {
        String sql = "INSERT INTO users (firstName, lastName, email, password, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, user.getFirstName());
                statement.setString(2, user.getLastName());
                statement.setString(3, user.getEmail());
                statement.setString(4, user.getPassword());
                statement.setString(5, user.getRole());

                int rows = statement.executeUpdate();
                if (rows == 0) return -1;

                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        return resultSet.getInt(1);
                    }
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean validateLogin(String email, String password) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ? AND password = ?";

        try (Connection connection = database.getConnection(); 
            PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, email);
            statement.setString(2, password);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getRoleByEmail(String email) {
        String sql = "SELECT role FROM users WHERE email = ?";

        try (Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
                
            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("role");
                }
            } 
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return null;
    }
}