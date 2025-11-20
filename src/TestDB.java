import java.sql.Connection;
import java.sql.DriverManager;

public class TestDB {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ENSF490_TermProject_DataBase?useSSL=false&serverTimezone=UTC",
                "root",
                "Queen-queen1"
            );
            System.out.println("Connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
