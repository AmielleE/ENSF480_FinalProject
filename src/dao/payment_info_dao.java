package dao;

import java.sql.*;
import model.PaymentInfo;

public class payment_info_dao {

    public boolean addPaymentInfo(PaymentInfo info) {
        String sql = "INSERT INTO payment_info (name, cardNumber, securityCode, expiryDate) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, info.getName());
            stmt.setString(2, info.getCardNumber());
            stmt.setInt(3, info.getSecurityCode());
            stmt.setString(4, info.getExpiryDate());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public PaymentInfo getPaymentInfo(String name) {
        String sql = "SELECT cardNumber, securityCode, expiryDate " +
                     "FROM payment_info WHERE name = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String card = rs.getString("cardNumber");
                    int code = rs.getInt("securityCode");
                    String expiry = rs.getString("expiryDate");

                    return new PaymentInfo(card, name, code, expiry);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updatePaymentInfo(PaymentInfo info) {
        String sql = "UPDATE payment_info SET cardNumber = ?, securityCode = ?, expiryDate = ? " +
                     "WHERE name = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, info.getCardNumber());
            stmt.setInt(2, info.getSecurityCode());
            stmt.setString(3, info.getExpiryDate());
            stmt.setString(4, info.getName());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePaymentInfo(String name) {
        String sql = "DELETE FROM payment_info WHERE name = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
