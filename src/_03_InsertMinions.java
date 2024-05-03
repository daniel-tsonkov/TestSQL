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

        /*Minions: Robert 14 Berlin
          Villains: Gru*/

        String[] minionInfo = scanner.nextLine().split(" ");
        String minionName = minionInfo[1];
        int minionAge = Integer.parseInt(minionInfo[2]);
        String minionTown = minionInfo[3];

        String villainName = scanner.nextLine().split(" ")[1];

        PreparedStatement selectTown = connection.prepareStatement("SELECT id FROM towns WHERE name = ?");
        selectTown.setString(1, minionTown);
        ResultSet townSet = selectTown.executeQuery();

        int townId = 0;
        if (!townSet.next()) {
            PreparedStatement insertTown = connection.prepareStatement("INSERT INTO towns(name) VALUES (?);");

            insertTown.setString(1, minionTown);
            ResultSet newTownSet = selectTown.executeQuery();
            townId = newTownSet.getInt("id");
        } else {
            townId = townSet.getInt("id");
        }

        System.out.println(townId);

        connection.close();
    }
}