package dao;

import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class users_dao {

    public boolean validateLogin(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (Exception e) {
            System.out.println("validateLogin error: " + e.getMessage());
        }
        return false;
    }

    public String getRoleByEmail(String email) {
        String sql = "SELECT role FROM users WHERE email = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rs.getString("role");

        } catch (Exception e) {
            System.out.println("getRoleByEmail error: " + e.getMessage());
        }
        return null;
    }

    public Customer getCustomerByEmail(String email) {
        String sql = "SELECT userID, firstName, lastName, email, password FROM users WHERE email = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getInt("userID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }

        } catch (Exception e) {
            System.out.println("getCustomerByEmail error: " + e.getMessage());
        }
        return null;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();

        String sql = "SELECT userID, firstName, lastName, email, password FROM users WHERE role = 'Customer'";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Customer c = new Customer(
                        rs.getInt("userID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("password")
                );
                list.add(c);
            }

        } catch (Exception e) {
            System.out.println("getAllCustomers error: " + e.getMessage());
        }

        return list;
    }

    public boolean insertCustomer(Customer c) {
        String sql = "INSERT INTO users (userID, firstName, lastName, email, password, role) VALUES (?, ?, ?, ?, ?, 'Customer')";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, c.getId());
            stmt.setString(2, c.getFirstName());
            stmt.setString(3, c.getLastName());
            stmt.setString(4, c.getEmail());
            stmt.setString(5, c.getPassword());

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("insertCustomer error: " + e.getMessage());
            return false;
        }
    }

    public boolean updateCustomer(int id, String fname, String lname, String email, String password) {
        String sql = "UPDATE users SET firstName = ?, lastName = ?, email = ?, password = ? WHERE userID = ? AND role = 'Customer'";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fname);
            stmt.setString(2, lname);
            stmt.setString(3, email);
            stmt.setString(4, password);
            stmt.setInt(5, id);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("updateCustomer error: " + e.getMessage());
            return false;
        }
    }

    public boolean removeCustomer(int id) {
        String sql = "DELETE FROM users WHERE userID = ? AND role = 'Customer'";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("removeCustomer error: " + e.getMessage());
            return false;
        }
    }
}
