import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class _05_ChangeTownsNames {
    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", "test");
        properties.setProperty("password", "aassdd");

        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.5.200:3306/minions_db", properties);

        Scanner scanner = new Scanner(System.in);
        String countryName = scanner.nextLine();

        PreparedStatement updateTownNames = connection.prepareStatement("UPDATE towns SET name = UpPER(name) WHERE country = ?");
        updateTownNames.setString(1, countryName);
        int updatedCount = updateTownNames.executeUpdate();

        if (updatedCount == 0) {
            System.out.println("No town names were affected.");
            return;
        }

        System.out.printf("%d town names were affected.", updatedCount);

        PreparedStatement selectAllTowns = connection.prepareStatement("SELECT name FROM towns WHERE country = ?");
        selectAllTowns.setString(1, countryName);
        ResultSet townSet = selectAllTowns.executeQuery();

        List<String> towns = new ArrayList<>();

        while (townSet.next()) {
            String townName = townSet.getString("name");
            towns.add(townName);
        }

        System.out.println(towns);

        connection.close();
    }
}
