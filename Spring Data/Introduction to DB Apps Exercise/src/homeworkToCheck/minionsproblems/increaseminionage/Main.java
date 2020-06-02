package minionsproblems.increaseminionage;

import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "minions_db";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "InTheClub454494";

    public static void main(String[] args) throws SQLException {
        System.out.println("Please enter minions id");
        Scanner input = new Scanner(System.in);
        int [] ids = Arrays.stream(input.nextLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();
        Connection connection = DriverManager.getConnection(CONNECTION_STRING + DATABASE_NAME, USER_NAME, PASSWORD);
        PreparedStatement prepStat = connection.prepareStatement(
                "UPDATE minions\n" +
                "SET age = age+1, name = lcase(name)\n" +
                "WHERE id = ?;");
        for (Integer i: ids){
            prepStat.setInt(1,i);
            prepStat.executeUpdate();
        }
        ResultSet rs = connection.prepareStatement("SELECT name,age FROM minions;").executeQuery();
        while (rs.next()){
            System.out.printf("%s %d%n",rs.getString("name"), rs.getInt("age"));
        }
        connection.close();
    }
}
