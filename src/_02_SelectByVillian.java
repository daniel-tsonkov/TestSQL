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

        PreparedStatement statement = connection.prepareStatement("SELECT name FROM villains WHERE id = ?");

        statement.setInt(1, villainId); //prevent from SQL injections

        ResultSet resultSet = statement.executeQuery();

        if (!resultSet.next()) {
            System.out.printf("No villain with ID %d exist in the database.", villainId);
            return;
        } else {
            String villainName = resultSet.getString("name");
            System.out.println("Villain: " + villainName);

            PreparedStatement minionsStatement = connection.prepareStatement(
                    "SELECT name, age " +
                    "FROM minions AS m " +
                    "JOIN minions_villains AS mv ON mv.minion_id = m.id " +
                    "WHERE mv.villain_id = 1;");

            ResultSet minionSet = minionsStatement.executeQuery();

            for (int i = 1; minionSet.next(); i++) {
                String name = minionSet.getString("name");
                int age = minionSet.getInt("age");

                System.out.printf("%d. %s %d%n", i, name, age);
            }
        }

        connection.close();
    }
}
