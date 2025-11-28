package testdb;

import dao.database;
import java.sql.Connection;
import java.sql.Statement;

public class SeedDatabase {

    public static void main(String[] args) {

        String sql =
        "INSERT INTO users (firstName, lastName, email, password, role) VALUES " +
        "('Admin', 'Admin', 'admin@admin.com', 'admin', 'Admin')," +
        "('Agent', 'Agent', 'agent@agent.com', 'agent', 'Agent');" +

        "INSERT INTO planes (aircraftID, model, airline, rows, cols, capacity) VALUES " +
        "('A320-CA1', 'Airbus A320', 'Air Canada', 30, 6, 180)," +
        "('B737-WS1', 'Boeing 737-800', 'WestJet', 28, 6, 168)," +
        "('DH8D-PO1', 'Dash 8 Q400', 'Porter', 20, 4, 80);" +

        "INSERT INTO flights (flightID, origin, destination, flightDate, flightTime, price, planeID, seatsAvailable) VALUES " +
        "('AC101', 'Calgary', 'Vancouver', '2025-12-01', '09:00', 189.99, 'A320-CA1', 180)," +
        "('WS202', 'Vancouver', 'Calgary', '2025-12-01', '14:00', 175.49, 'B737-WS1', 168)," +
        "('AC303', 'Calgary', 'Toronto', '2025-12-02', '07:30', 299.99, 'A320-CA1', 180)," +
        "('PO707', 'Calgary', 'Edmonton', '2025-12-04', '08:15', 99.99, 'DH8D-PO1', 80)," +
        "('PO808', 'Edmonton', 'Calgary', '2025-12-04', '11:30', 102.50, 'DH8D-PO1', 80)," +
        "('AC909', 'Winnipeg', 'Toronto', '2025-12-05', '13:20', 210.00, 'A320-CA1', 180)," +
        "('WS010', 'Montreal', 'Halifax', '2025-12-05', '17:45', 165.25, 'B737-WS1', 168);";


        try (Connection conn = database.getConnection();
            Statement stmt = conn.createStatement()) {

            // wipe existing data
            stmt.executeUpdate("DELETE FROM bookings;");
            stmt.executeUpdate("DELETE FROM flights;");
            stmt.executeUpdate("DELETE FROM planes;");
            stmt.executeUpdate("DELETE FROM users;");

            // now run inserts
            stmt.executeUpdate(sql);

            System.out.println("Database seeded successfully!");
        } catch (Exception e) {
            System.out.println("Error seeding: " + e.getMessage());
        }
    }
}
