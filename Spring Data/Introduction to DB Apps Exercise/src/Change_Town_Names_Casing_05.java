import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;


public class Change_Town_Names_Casing_05 {

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

        System.out.print("Enter a country:  ");
        String countryToUpdate = reader.readLine().trim();

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE towns SET name = UPPER(name) WHERE country = ? AND name NOT LIKE BINARY UPPER(name) ");
        preparedStatement.setString(1, countryToUpdate);

        int affectedRows = preparedStatement.executeUpdate();
        System.out.println("-".repeat(50));
        if (affectedRows > 0) {
            preparedStatement = connection.prepareStatement("SELECT name FROM towns WHERE country = ?");
            preparedStatement.setString(1, countryToUpdate);
            ResultSet updatedCountriesRS = preparedStatement.executeQuery();

            String result = "[";
            while (updatedCountriesRS.next()) {
                result += updatedCountriesRS.getString("name") + ", ";
            }
            result = result.substring(0, result.length() - 2);
            result += "]";

            System.out.println(affectedRows + " town names were affected.");
            System.out.println(result);
        } else {
            System.out.println("No town names were affected.");
        }
    }
}
