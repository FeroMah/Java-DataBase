import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;


public class Get_Villains_Names_02 {
    public static void main(String[] args) throws IOException, SQLException {
        // TODO: 2/18/2020 automate creation and update fresh minions_db
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter username (default root) :   ");

        String user = reader.readLine();
        user = user.equals("") ? "root" : user;
        System.out.print("Enter password (default) :    ");
        String password = reader.readLine();
        password = password.equals("") ? "InTheClub454494" : password;

        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", properties);
        String queryStr = "SELECT v.name AS villain_name, COUNT(mv.minion_id) AS minions_count\n" +
                "FROM villains AS v\n" +
                "         INNER JOIN minions_villains mv on v.id = mv.villain_id\n" +
                "GROUP BY v.name\n" +
                "HAVING minions_count > 15\n" +
                "ORDER BY minions_count DESC;";
        PreparedStatement statement = connection.prepareStatement(queryStr);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            String toPrint = String.format("| %-15.15s | %-15.15s |", resultSetMetaData.getColumnLabel(1)
                    , resultSetMetaData.getColumnLabel(2));
            System.out.println();
            System.out.println(toPrint);
            System.out.println("-".repeat(toPrint.length()));
            resultSet.beforeFirst();
        }

        while (resultSet.next()) {
            System.out.println(String.format("| %-15.15s | %-15.15s |",
                    resultSet.getString("villain_name"), resultSet.getInt("minions_count")));
        }
        connection.close();
    }
}
