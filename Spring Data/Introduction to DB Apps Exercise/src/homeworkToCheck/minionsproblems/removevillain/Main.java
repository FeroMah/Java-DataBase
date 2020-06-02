package minionsproblems.removevillain;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "minions_db";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "InTheClub454494";

    public static void main(String[] args) throws SQLException {
    Scanner input = new Scanner(System.in);
        System.out.println("Please enter villain's ID");
    int id = Integer.parseInt(input.nextLine());
    int released = 0;
    String name = "";
        Connection connection = DriverManager.getConnection(
                CONNECTION_STRING + DATABASE_NAME, USER_NAME, PASSWORD);
        PreparedStatement prepStat = connection.prepareStatement(
                "SELECT name, count(*) as c FROM villains\n" +
                        "WHERE id = ?;");
        prepStat.setInt(1,id);
        ResultSet rs = prepStat.executeQuery();
        rs.next();
        if(rs.getInt("c")>0) {
            name = rs.getString("name");
        }
        else{
            System.out.println("No such villain was found");
            connection.close();
            return;
        }
        prepStat = connection.prepareStatement(
                "DELETE FROM minions_villains\n" +
                "WHERE villain_id = ?;");
        prepStat.setInt(1,id);
        released = prepStat.executeUpdate();

        prepStat = connection.prepareStatement(
                "DELETE FROM villains\n" +
                "WHERE id = ?;");
        prepStat.setInt(1,id);
        prepStat.executeUpdate();

        System.out.printf("%s was deleted%n",name);
        System.out.printf("%d minions released%n",released);

        connection.close();
    }
}
