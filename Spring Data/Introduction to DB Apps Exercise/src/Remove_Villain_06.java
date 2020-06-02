import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class Remove_Villain_06 {
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

        connection.setAutoCommit(false);

        System.out.print("Villain id:   ");
        int villaindId = Integer.parseInt(reader.readLine().trim());
        int releasedMinions = 0;


        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM villains WHERE id = ?");
        preparedStatement.setInt(1, villaindId);
        ResultSet villainNameRS = preparedStatement.executeQuery();
        try {
            villainNameRS.next();
        }catch (Exception e){
            System.out.println("No such villain was found!");
        }

        String name = villainNameRS.getString("name");
        System.out.println("-".repeat(50));
        if (name.equals("")) {
            System.out.println("No such villain was found!");
        } else {
            preparedStatement = connection.prepareStatement("DELETE FROM minions_villains WHERE villain_id = ?");
            preparedStatement.setInt(1, villaindId);
            releasedMinions = preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("DELETE FROM villains WHERE id = ?");
            preparedStatement.setInt(1, villaindId);
            preparedStatement.executeUpdate();

            connection.commit();

            System.out.println(name + " was deleted.");
            System.out.println(releasedMinions + " minions released.");
        }
    }
}