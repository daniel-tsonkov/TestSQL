import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class _06_DeleteVillain {
    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", "test");
        properties.setProperty("password", "aassdd");

        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.5.200:3306/minions_db", properties);

        Scanner scanner = new Scanner(System.in);
        int villainId = Integer.parseInt(scanner.nextLine());

        connection.setAutoCommit(false);

        PreparedStatement statement = connection.prepareStatement("SELECT name FROM villains WHERE id = ?");

        statement.setInt(1, villainId);
        statement.executeQuery();

        try {

        } catch (SQLException e) {
            connection.rollback();
        }


        ResultSet resultSet = statement.executeQuery();



        connection.close();
    }
}
