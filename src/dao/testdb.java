package dao;

import dao.database;
import java.sql.Connection;

public class testdb {
    public static void main(String[] args) {
        try (Connection conn = database.getConnection()) {
            System.out.println("Connected to SQLite successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
