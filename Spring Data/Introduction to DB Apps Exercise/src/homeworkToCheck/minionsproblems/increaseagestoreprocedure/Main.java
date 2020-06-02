package minionsproblems.increaseagestoreprocedure;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "minions_db";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "InTheClub454494";

    public static void main(String[] args) throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter minion's id");
        int id = Integer.parseInt(input.nextLine());

        Connection connection = DriverManager.getConnection(CONNECTION_STRING + DATABASE_NAME, USER_NAME, PASSWORD);

        CallableStatement cStat = connection.prepareCall(
                "CALL usp_get_older(?);");
        cStat.setInt(1,id);
        cStat.executeQuery();

        PreparedStatement prepStat = connection.prepareStatement(
                "SELECT name, age FROM minions\n" +
                "WHERE id = ?;");
        prepStat.setInt(1,id);
        ResultSet rs = prepStat.executeQuery();
        while (rs.next()){
            System.out.printf("%s %d%n",rs.getString("name"),rs.getInt("age"));
        }
        connection.close();
    }
}
