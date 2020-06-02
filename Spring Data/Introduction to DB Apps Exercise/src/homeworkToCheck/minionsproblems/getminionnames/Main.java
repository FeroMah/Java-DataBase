package minionsproblems.getminionnames;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "minions_db";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "InTheClub454494";

    public static void main(String[] args) throws SQLException {
        Scanner input = new Scanner(System.in);
        Properties prop = new Properties();
        prop.setProperty("user",USER_NAME);
        prop.setProperty("password",PASSWORD);
        Connection connection = DriverManager.getConnection(CONNECTION_STRING + DATABASE_NAME,prop);

        PreparedStatement prepStat = connection.prepareStatement(
                "SELECT distinct m.name,m.age,v.name AS `v_name` FROM minions AS m\n" +
                        "JOIN minions_villains as mv\n" +
                        "on m.id = mv.minion_id\n" +
                        "JOIN villains AS v\n" +
                        "ON mv.villain_id = v.id\n" +
                        "WHERE mv.villain_id = ?;");
        System.out.println("Please enter villain's ID");
        int id = Integer.parseInt(input.nextLine());
        prepStat.setInt(1,id);
        ResultSet rs = prepStat.executeQuery();

        int n = 0;
        boolean exists = rs.next();
        rs.previous();
        while (rs.next()){
            if(n==0){
                System.out.println("Villain: " + rs.getString("v_name"));
            }
            n++;
            System.out.printf("%d. %s %d%n", n, rs.getString("name"), rs.getInt("age"));
        }
        if(!exists){
            System.out.printf("No villain with ID %d exists in the database.",id);
        }

        connection.close();
    }

}
