package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Plane;

public class planes_dao {
    public boolean addPlane(Plane plane) {
        String sql = "INSERT INTO planes (aircraftID, model, airline, rows, cols, capacity) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, plane.getAircraftID());
            statement.setString(2, plane.getModel());
            statement.setString(3, plane.getAirline());
            statement.setInt(4, plane.getRows());
            statement.setInt(5, plane.getCols());
            statement.setInt(6, plane.getCapacity());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public Plane getPlaneByID(String aircraftID) {
        String sql = "SELECT aircraftID, model, airline, rows, cols, capacity " +
                    "FROM planes WHERE aircraftID = ?";

        try (Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, aircraftID);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String id = resultSet.getString("aircraftID");
                    String model = resultSet.getString("model");
                    String airline = resultSet.getString("airline");
                    int rows = resultSet.getInt("rows");
                    int cols = resultSet.getInt("cols");
                    int capacity = resultSet.getInt("capacity");

                    return new Plane(id, model, airline, rows, cols, capacity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<Plane> getAllPlanes() {
        List<Plane> planes = new ArrayList<>();

        String sql = "SELECT aircraftID, model, airline, rows, cols, capacity FROM planes";

        try (Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String id      = resultSet.getString("aircraftID");
                String model   = resultSet.getString("model");
                String airline = resultSet.getString("airline");
                int rows       = resultSet.getInt("rows");
                int cols       = resultSet.getInt("cols");
                int capacity   = resultSet.getInt("capacity");

                Plane p = new Plane(id, model, airline, rows, cols, capacity);
                planes.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return planes;
    }


    public boolean updatePlane(Plane plane) {
        String sql = "UPDATE planes SET model = ?, airline = ?, rows = ?, cols = ?, capacity = ?" + "WHERE aircraftID = ?";

        try (Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, plane.getModel());
                statement.setInt(2, plane.getRows());
                statement.setInt(3, plane.getCols());
                statement.setInt(4, plane.getRows() * plane.getCols());
                statement.setString(5, plane.getAircraftID());

                int rowsUpdated = statement.executeUpdate();
                return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePlane(String aircraftID) {
        String sql = "DELETE FROM planes WHERE aircraftID = ?";

        try (Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, aircraftID);
                int rowsDeleted = statement.executeUpdate();
                return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
