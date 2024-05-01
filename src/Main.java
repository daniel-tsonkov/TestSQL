import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", "test");
        properties.setProperty("password", "aassdd");

        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.5.200:3306/minions_db", properties);

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM villains");
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String villainName = resultSet.getString("name");

            System.out.println(villainName);
        }


        connection.close();
    }

}
