import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class _03_InsertMinions {
    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", "test");
        properties.setProperty("password", "aassdd");

        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.5.200:3306/minions_db", properties);

        Scanner scanner = new Scanner(System.in);

        String[] minionInfo = scanner.nextLine().split(" ");
        String[] villainInfo = scanner.nextLine().split(" ");


        connection.close();
    }
}
