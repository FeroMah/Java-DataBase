import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Print_All_Minion_Names_07 {
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

        List<String> minionsName = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM minions");
        ResultSet resultSet = preparedStatement.executeQuery();

        System.out.println("-".repeat(50));
        while (resultSet.next()) {
            minionsName.add(resultSet.getString("name"));
        }

        for (int i = 0; i < minionsName.size() / 2; i++) {
            System.out.println(minionsName.get(i));
            System.out.println(minionsName.get(minionsName.size() - 1 - i));
        }

        if ((double) minionsName.size() % 2 != 0) {
            System.out.println(minionsName.get((int) Math.ceil(minionsName.size() / 2)));
        }
    }
}
