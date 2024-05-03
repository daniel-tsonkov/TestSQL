import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", "test");
        properties.setProperty("password", "aassdd");

        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.5.200:3306/minions_db", properties);

        PreparedStatement statement = connection.prepareStatement("" +
                "SELECT name, COUNT(mv.minion_id) as minion_count from villains as v " +
                "JOIN minions_villains as mv on mv.villain_id = v.id " +
                "GROUP by mv.villain_id " +
                "HAVING minion_count > 15 " +
                "ORDER  BY minion_count DESC;");
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String villainName = resultSet.getString("name");
            int minionCount = resultSet.getInt("minion_count");

            System.out.println(villainName + " " + minionCount);
        }

        connection.close();
    }
}
