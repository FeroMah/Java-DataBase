package minionsproblems.addminion;

import java.sql.*;
import java.util.Scanner;

import static java.sql.DriverManager.getConnection;

public class Main {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "minions_db";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "InTheClub454494";
    private static Connection connection;

    public static void main(String[] args) throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter minion and villain data");
        String [] minionData = input.nextLine().split(" ");
        String minionName = minionData[1];
        int minionAge = Integer.parseInt(minionData[2]);
        String minionTown = minionData[3];
        String villainName = input.nextLine().split(" ")[1];
        connection = getConnection(CONNECTION_STRING + DATABASE_NAME, USER_NAME, PASSWORD);

       boolean townExists = isEntityExisting("towns",minionTown);

        if(!townExists){
               PreparedStatement prepStatInsertTown = connection.prepareStatement(
                        "INSERT INTO towns(name)\n" +
                        "VALUE (?);");
                prepStatInsertTown.setString(1,minionTown);
                prepStatInsertTown.executeUpdate();
                System.out.printf("Town %s was added to the database.%n",minionTown);
            }

        boolean villainExists = isEntityExisting("villains",villainName);
            if(!villainExists){
                PreparedStatement prepStatInsertVillain = connection.prepareStatement(
                        "INSERT INTO villains(name,evilness_factor)\n" +
                        "VALUES (?,'evil');");
                prepStatInsertVillain.setString(1,villainName);
               prepStatInsertVillain.executeUpdate();
                System.out.printf("Villain %s was added to the database.%n",villainName);
            }

        PreparedStatement prepInsert = connection.prepareStatement(
            "INSERT minions(name,age, town_id)\n" +
                    "VALUES (\n" +
                    "?, \n" +
                    "?,\n" +
                    "(SELECT id FROM towns WHERE name = ?));");
        prepInsert.setString(1,minionName);
        prepInsert.setInt(2,minionAge);
        prepInsert.setString(3,minionTown);
        prepInsert.executeUpdate();

        prepInsert = connection.prepareStatement(
                "INSERT INTO minions_villains(minion_id, villain_id)\n" +
                        "VALUES(\n" +
                        "(SELECT id FROM minions WHERE name = ?),\n" +
                        " (SELECT id FROM villains WHERE name = ?)\n" +
                        " );");
        prepInsert.setString(1,minionName);
        prepInsert.setString(2,villainName);
        prepInsert.executeUpdate();
        System.out.printf("Successfully added %s to be minion of %s.%n", minionName, villainName);
        connection.close();
    }

    private static boolean isEntityExisting(String tableName, String name) throws SQLException {
        PreparedStatement prepStat = connection.prepareStatement(
                "SELECT name FROM " + tableName + "\n" +
                        "WHERE name = ?;");

        prepStat.setString(1,name);
        ResultSet rs = prepStat.executeQuery( );
        return  rs.next();
    }
}
