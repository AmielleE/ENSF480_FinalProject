package dao;

import java.sql.*;
import model.Plane;

public class planes_dao {
    public boolean addPlane(Plane plane) {
        String sql = "INSERT INTO planes (aircraftID, model, numRows, seatsPerRow, capacity) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, plane.getAircraftID());
                statement.setString(2, plane.getModel());
                statement.setInt(3, plane.getRows());
                statement.setInt(4, plane.getCols());
                statement.setInt(5, plane.getRows() * plane.getCols());

                statement.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    }

    public Plane getPlaneByID(String aircraftID) {
        String sql = "SELECT aircraftID, model, numRows, seatsPerRow" + "FROM planes WHERE aircraftID = ?";

        try (Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, aircraftID);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String id = resultSet.getString("aircraftID");
                        String model = resultSet.getString("model");
                        int rows = resultSet.getInt("numRows");
                        int cols = resultSet.getInt("seatsPerRow");

                        return new Plane(id, model, "", rows, cols);

                    }
                }
            }
    }
}
