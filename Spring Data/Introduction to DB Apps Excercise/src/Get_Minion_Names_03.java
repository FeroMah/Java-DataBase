import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class Get_Minion_Names_03 {
    public static void main(String[] args) throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter username (default root) :   ");
        String user = reader.readLine();
        user = user.equals("") ? "root" : user;

        System.out.print("Enter password (default):   ");
        String password = reader.readLine();
        password = password.equals("") ? "InTheClub454494" : password;

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/minions_db?useSSL=false", props);

        System.out.print("Enter villain id: ");
        int villainIdInput=0;
        try {
             villainIdInput = Integer.parseInt(reader.readLine());
        }catch (Exception e){
            System.out.println("No id was given!");
            return;
        }


        if (hasVillainWithId(connection, villainIdInput)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT m.name AS `minion_name`, m.age AS `minion_age`, v.name AS `villain_name` FROM minions AS `m`\n" +
                    "JOIN minions_villains AS `mv`\n" +
                    "ON mv.minion_id = m.id\n" +
                    "JOIN villains AS `v`\n" +
                    "ON v.id = mv.villain_id\n" +
                    "WHERE v.id = ?");
            preparedStatement.setInt(1, villainIdInput);
            ResultSet resultSet = preparedStatement.executeQuery();
            int counter = 0;
            while (resultSet.next()) {
                System.out.println(String.format("%d. %s %d", ++counter, resultSet.getString("minion_name"), resultSet.getInt("minion_age")));
            }
        }
    }


    private static boolean hasVillainWithId(Connection connection, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM villains WHERE id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("-".repeat(50)); // just to be more readable result in the console
        if (resultSet.next()) {
            System.out.println(String.format("Villain:  %s", resultSet.getString("name")));
            return true;
        } else {
            System.out.println(String.format("No villain with ID %s exists in the database.", id));
            return false;
        }
    }
}
