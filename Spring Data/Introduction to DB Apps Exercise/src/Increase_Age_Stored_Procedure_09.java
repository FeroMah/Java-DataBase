import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;


public class Increase_Age_Stored_Procedure_09 {
    public static void main(String[] args) throws SQLException, IOException {

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

        PreparedStatement preparedStatement = connection.prepareStatement("call usp_get_older(?)");
        System.out.print("Enter minion ID:  ");
        int minionId = Integer.parseInt(reader.readLine());

        preparedStatement.setInt(1, minionId);
        ResultSet resultSet = preparedStatement.executeQuery();

        System.out.println("-".repeat(50));
        while (resultSet.next()) {
            System.out.println(resultSet.getString("name") + " " + resultSet.getString("age"));
        }
    }
}
