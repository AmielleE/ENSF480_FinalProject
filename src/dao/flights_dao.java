package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.*;
import dao.*;

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

    public List<Flight> searchFlights(String origin, String destination, String date) {
        List<Flight> flights = new ArrayList<>();

        String sql = "SELECT * FROM flights WHERE origin = ? AND destination = ? AND flightDate = ?";

        try (Connection conn = database.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, origin);
            ps.setString(2, destination);
            ps.setString(3, date);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String flightID   = rs.getString("flightID");
                String flightDate = rs.getString("flightDate");
                String flightTime = rs.getString("flightTime");
                double price      = rs.getDouble("price");
                String planeID    = rs.getString("planeID");
                int seatsAvail    = rs.getInt("seatsAvailable");

                // Fetch Plane object
                planes_dao pdao = new planes_dao();
                Plane plane = pdao.getPlaneByID(planeID);

                // Build Flight object
                Flight f = new Flight(
                    flightID,
                    origin,
                    destination,
                    flightDate,
                    flightTime,  // stored as departure
                    "",          // arrival time (you can fill later)
                    price,
                    plane
                );

                f.setSeatsAvailable(seatsAvail);

                flights.add(f);
            }

        } catch (SQLException e) {
            System.out.println("searchFlights error: " + e.getMessage());
        }

        return flights;
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
        List<Flight> list = new ArrayList<>();

        String sql = "SELECT * FROM flights";

        try (Connection conn = database.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String flightID = rs.getString("flightID");
                String origin = rs.getString("origin");
                String dest = rs.getString("destination");
                String date = rs.getString("flightDate");
                String time = rs.getString("flightTime");
                double price = rs.getDouble("price");
                String planeID = rs.getString("planeID");
                int seats = rs.getInt("seatsAvailable");

                Plane plane = new planes_dao().getPlaneByID(planeID);
                Flight f = new Flight(flightID, origin, dest, date, time, "", price, plane);
                f.setSeatsAvailable(seats);

                list.add(f);
            }
        } catch (Exception e) {
            System.out.println("getAllFlights error: " + e.getMessage());
        }
        return list;
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
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, flightID);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("DeleteFlight error: " + e.getMessage());
            return false;
        }
    }

    public boolean insertFlight(Flight f) {
        String sql = "INSERT INTO flights VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = database.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, f.getFlightID());
            ps.setString(2, f.getOrigin());
            ps.setString(3, f.getDestination());
            ps.setString(4, f.getDate());
            ps.setString(5, f.getDepartureTime()); 
            ps.setDouble(6, f.getPrice());
            ps.setString(7, f.getPlane().getAircraftID());
            ps.setInt(8, f.getPlane().getCapacity()); // initial seats available

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("InsertFlight error: " + e.getMessage());
            return false;
        }
    }
}
