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

        /*Minions: Cathleen 20 Liverpool
        Villains: Gru*/

        String[] minionInfo = scanner.nextLine().split(" ");
        String minionName = minionInfo[1];
        int minionAge = Integer.parseInt(minionInfo[2]);
        String minionTown = minionInfo[3];

        String villainName = scanner.nextLine().split(" ")[1];

        int townId = getOrInsertTown(connection, minionTown);
        int villainId = getOrInsertVillain(connection, villainName);

        System.out.println(townId);

        connection.close();
    }

    private static int getOrInsertVillain(Connection connection, String villainName) throws SQLException {
        PreparedStatement selectVillain = connection.prepareStatement("SELECT id FROM villains WHERE name = ?");
        selectVillain.setString(1, villainName);

        ResultSet villainSet = selectVillain.executeQuery();

        int villainId = 0;
        if (!villainSet.next()) {
            PreparedStatement insertVillain = connection.prepareStatement("INSERT INTO villains(name, evilness_factor) VALUE(?, ?)");
            insertVillain.setString(1, villainName);
            insertVillain.setString(2, "evil");

            insertVillain.executeUpdate();

            ResultSet newVillainSet = selectVillain.executeQuery();
            newVillainSet.next();
            villainId = newVillainSet.getInt("id");
        } else {
            villainId = villainSet.getInt("id");
        }

        return villainId;
    }

    private static int getOrInsertTown(Connection connection, String minionTown) throws SQLException {
        PreparedStatement selectTown = connection.prepareStatement("SELECT id FROM towns WHERE name = ?");
        selectTown.setString(1, minionTown);
        ResultSet townSet = selectTown.executeQuery();

        int townId;
        if (!townSet.next()) {
            PreparedStatement insertTown = connection.prepareStatement("INSERT INTO towns(name) VALUES (?);");

            insertTown.setString(1, minionTown);
            insertTown.executeUpdate();

            ResultSet newTownSet = selectTown.executeQuery();
            newTownSet.next();
            townId = newTownSet.getInt("id");
        } else {
            townId = townSet.getInt("id");
        }

        return townId;
    }
}
