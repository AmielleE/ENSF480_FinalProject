package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Flight;
import model.Plane;

public class flights_dao {
    private planes_dao planesDao = new planes_dao();

    public boolean addFlight(Flight flight) {
        String sql = "INSERT INTO flights (" + "flightID, origin, destination, flightDate, flightTime, price, planeID, seatsAvailabe" + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, flight.getFlightID());
                statement.setString(2, flight.getOrigin());
                statement.setString(3, flight.getDestination());
                statement.setString(4, flight.getDate());
                statement.setString(5, flight.getDepartureTime());
                statement.setDouble(6, flight.getPrice());

                Plane plane = flight.getPlane();
                statement.setString(7, plane.getAircraftID());

                int capacity = plane.getRows() * plane.getCols();
                statement.setInt(8, capacity);

                statement.executeUpdate();
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Flight getFlightById(String flightID) {
        String sql = "SELECT flightID, origin, destination, flightDate, flightTime, price, planeID " +
                     "FROM flights WHERE flightID = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, flightID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    String id     = rs.getString("flightID");
                    String origin = rs.getString("origin");
                    String dest   = rs.getString("destination");
                    String date   = rs.getString("flightDate");
                    String time   = rs.getString("flightTime");
                    double price  = rs.getDouble("price");
                    String planeID = rs.getString("planeID");

                    Plane plane = planesDao.getPlaneByID(planeID);

                    return new Flight(id, origin, dest, date, time, "", price, plane);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Flight> getAllFlights() {
        List<Flight> flights = new ArrayList<>();

        String sql = "SELECT flightID, origin, destination, flightDate, flightTime, price, planeID FROM flights";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                String id     = rs.getString("flightID");
                String origin = rs.getString("origin");
                String dest   = rs.getString("destination");
                String date   = rs.getString("flightDate");
                String time   = rs.getString("flightTime");
                double price  = rs.getDouble("price");
                String planeID = rs.getString("planeID");

                Plane plane = planesDao.getPlaneByID(planeID);

                Flight f = new Flight(id, origin, dest, date, time, "", price, plane);
                flights.add(f);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flights;
    }

    public boolean updateFlight(Flight flight) {
        String sql = "UPDATE flights SET " +
                     "origin = ?, destination = ?, flightDate = ?, flightTime = ?, " +
                     "price = ?, planeID = ?, seatsAvailable = ? " +
                     "WHERE flightID = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, flight.getOrigin());
            stmt.setString(2, flight.getDestination());
            stmt.setString(3, flight.getDate());
            stmt.setString(4, flight.getDepartureTime());
            stmt.setDouble(5, flight.getPrice());

            Plane p = flight.getPlane();
            stmt.setString(6, p.getAircraftID());

            int capacity = p.getRows() * p.getCols();
            stmt.setInt(7, capacity);

            stmt.setString(8, flight.getFlightID());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteFlight(String flightID) {
        String sql = "DELETE FROM flights WHERE flightID = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, flightID);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
