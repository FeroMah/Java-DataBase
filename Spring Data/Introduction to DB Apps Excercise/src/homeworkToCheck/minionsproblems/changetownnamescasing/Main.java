package minionsproblems.changetownnamescasing;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "minions_db";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "InTheClub454494";

    public static void main(String[] args) throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter country");
        String countryName = input.nextLine();
        int affectedRows = 0;

        Connection connection = DriverManager.getConnection(CONNECTION_STRING + DATABASE_NAME, USER_NAME, PASSWORD);

        PreparedStatement prepStat = connection.prepareStatement(
                "UPDATE towns\n" +
                "SET name = upper(name)\n" +
                "WHERE country = ?;");
        prepStat.setString(1,countryName);
        affectedRows = prepStat.executeUpdate();

        prepStat = connection.prepareStatement(
                "SELECT name FROM towns\n" +
                "WHERE country = ?;");
        prepStat.setString(1,countryName);
        ResultSet rs = prepStat.executeQuery();
        List<String> townList = new ArrayList();
        while (rs.next()){
            townList.add(rs.getString("name"));
        }
        if(affectedRows>0) {
            System.out.printf("%d town names were affected.%n", affectedRows);
            System.out.println("[" + String.join(", ", townList) + "]");
        }
        else {
            System.out.println("No town names were affected.");
        }
        connection.close();
    }
}
