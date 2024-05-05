import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

        PreparedStatement selectVillain = connection.prepareStatement("SELECT name FROM villains WHERE id = ?");

        selectVillain.setInt(1, villainId);
        ResultSet villainSet = selectVillain.executeQuery();

        if (!villainSet.next()) {
            System.out.println("No such villain was found");
            return;
        }

        String villainName = villainSet.getString("name");
        connection.setAutoCommit(false);

        List<Integer> minionsIds = new ArrayList<>();
        PreparedStatement selectAllVillainsMinions = connection.prepareStatement(
                "SELECT COUNT(DISTINCT minion_id) as m_count FROM minions_villains WHERE villain_id = ?");
        selectAllVillainsMinions.setInt(1, villainId);
        ResultSet minionsCountSet = selectAllVillainsMinions.executeQuery();

        minionsCountSet.next();
        int countMinionsDeleted = minionsCountSet.getInt("m_count");

        try {
            PreparedStatement deleteMinionsVillains = connection.prepareStatement("DELETE FROM minions_villains WHERE villain_id = ?");
            deleteMinionsVillains.setInt(1, villainId);
            deleteMinionsVillains.executeUpdate();

            PreparedStatement deleteVillain = connection.prepareStatement("DELETE FROM villain WHERE id = ?");
            deleteVillain.setInt(1, villainId);
            deleteVillain.executeUpdate();

            /*PreparedStatement deleteMinions = connection.prepareStatement("DELETE FROM minions WHERE id IN = ?");
            deleteMinions.setInt(1, minionsIds);
            int countMinionsDeleted = deleteMinions.executeUpdate();*/

            connection.commit();
            System.out.println(villainName + " was deleted");
            System.out.println(countMinionsDeleted + " minions deleted");

        } catch (SQLException e) {
            connection.rollback();
        }

        connection.close();
    }
}
