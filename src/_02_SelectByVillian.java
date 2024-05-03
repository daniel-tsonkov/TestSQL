import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class _02_SelectByVillian {
    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", "test");
        properties.setProperty("password", "aassdd");

        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.5.200:3306/minions_db", properties);

        Scanner scanner = new Scanner(System.in);
        int villainId = Integer.parseInt(scanner.nextLine());

        PreparedStatement statement = connection.prepareStatement("SELECT name FROM villain WHERE id = ?");

        statement.setInt(1, villainId); //prevent from SQL injections

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            System.out.printf("No villain with ID %d exist in the database.", villainId);
            return;
        } else {
            String villainName = resultSet.getString("name");
            System.out.println("Villain: " + villainName);
        }

        connection.close();
    }
}
