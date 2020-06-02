package minionsproblems.getvillainsnames;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "minions_db";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "InTheClub454494";

    public static void main(String[] args) throws SQLException {
        Properties prop = new Properties();
        prop.setProperty("user",USER_NAME);
        prop.setProperty("password",PASSWORD);
        Connection connection = DriverManager.getConnection(CONNECTION_STRING + DATABASE_NAME,prop);
        PreparedStatement prepStat = connection.prepareStatement(
                "SELECT v.name,count(mv.minion_id) as c_minions FROM villains AS v\n" +
                "JOIN minions_villains as mv\n" +
                "ON v.id = mv.villain_id\n" +
                "GROUP BY mv.villain_id\n" +
                "HAVING c_minions > 15\n" +
                "ORDER BY c_minions DESC;");

        ResultSet rs = prepStat.executeQuery();
        while (rs.next()){
            System.out.printf("%s %d%n",rs.getString("name"),rs.getLong("c_minions"));
        }
        connection.close();
    }
}
