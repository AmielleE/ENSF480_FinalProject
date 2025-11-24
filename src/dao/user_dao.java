package dao;

import java.sql.*;
import model.User;

public class user_dao {
    public boolean addUser(User user) {
        String sql = "INSERT INTO users (firstName, lastName, email, password, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, user.getFirstName());
                stmt.setString(2, user.getLastName());
                stmt.setString(3, user.getEmail());
                stmt.setString(4, user.getPassword());
                stmt.setString(5, user.getRole());

                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
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