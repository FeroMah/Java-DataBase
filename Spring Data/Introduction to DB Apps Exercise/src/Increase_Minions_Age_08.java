import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;


public class Increase_Minions_Age_08 {
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

        System.out.print("Minions ID:   ");
        int[] minionsId = Arrays.stream(reader.readLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE minions SET age = age + 1, name =  LOWER (name) WHERE id =?;");

        for (int i = 0; i < minionsId.length; i++) {
            preparedStatement.setInt(1,minionsId[i]);
            preparedStatement.executeUpdate();
        }

        preparedStatement = connection.prepareStatement("SELECT name, age FROM minions");

        ResultSet allMinionsRS = preparedStatement.executeQuery();

        System.out.println("-".repeat(50));
        while(allMinionsRS.next()){
            System.out.println(allMinionsRS.getString("name") + " " + allMinionsRS.getString("age"));
        }
    }
}
