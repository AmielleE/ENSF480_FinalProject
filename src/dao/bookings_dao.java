package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Booking;
import model.Flight;

public class bookings_dao {

    private flights_dao flightDao = new flights_dao();

    public boolean addBooking(Booking booking, int userID) {
        String sql = "INSERT INTO bookings " +
                     "(confirmationNumber, userID, flightID, seatsBooked) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, booking.getConfirmationNumber());
            stmt.setInt(2, userID);
            stmt.setString(3, booking.getFlight().getFlightID());
            stmt.setInt(4, booking.getSeatNumbers().size());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBooking(int confirmationNumber) {
        String sql = "DELETE FROM bookings WHERE confirmationNumber = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, confirmationNumber);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    

    public BookingSummary getBookingSummary(int confirmationNumber) {
        String sql = "SELECT flightID, seatsBooked FROM bookings WHERE confirmationNumber = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, confirmationNumber);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String flightID = rs.getString("flightID");
                    int seatsBooked = rs.getInt("seatsBooked");

                    Flight flight = flightDao.getFlightById(flightID);

                    return new BookingSummary(confirmationNumber, flight, seatsBooked);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Integer> getBookingConfirmationNumbersForUser(int userID) {
        List<Integer> list = new ArrayList<>();

        String sql = "SELECT confirmationNumber FROM bookings WHERE userID = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getInt("confirmationNumber"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static class BookingSummary {
        private final int confirmationNumber;
        private final Flight flight;
        private final int seatsBooked;

        public BookingSummary(int confirmationNumber, Flight flight, int seatsBooked) {
            this.confirmationNumber = confirmationNumber;
            this.flight = flight;
            this.seatsBooked = seatsBooked;
        }

        public int getConfirmationNumber() { return confirmationNumber; }
        public Flight getFlight() { return flight; }
        public int getSeatsBooked() { return seatsBooked; }
    }
}
