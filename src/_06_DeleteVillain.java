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

        PreparedStatement selectVillain = connection.prepareStatement("SELECT name FROM villains WHERE id = ?");

        selectVillain.setInt(1, villainId);
        ResultSet villainSet = selectVillain.executeQuery();

        if (!villainSet.next()) {
            System.out.println("No such villain was found");
            return;
        }

        try {
            PreparedStatement deleteMinionsVillains = connection.prepareStatement("DELETE FROM minions_villains WHERE villain_id = ?");
            deleteMinionsVillains.setInt(1, villainId);
            deleteMinionsVillains.executeUpdate();

            PreparedStatement deleteVillain = connection.prepareStatement("DELETE FROM villain WHERE id = ?");
            deleteVillain.setInt(1, villainId);
            deleteVillain.executeUpdate();

        } catch (SQLException e) {
            connection.rollback();
        }


        ResultSet resultSet = selectVillain.executeQuery();


        connection.close();
    }
}
