package minionsproblems.printallminionnames;

import java.sql.*;

public class Main {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "minions_db";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "InTheClub454494";

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(CONNECTION_STRING + DATABASE_NAME, USER_NAME, PASSWORD);
        ResultSet rs = connection.prepareStatement(
                "SELECT name,c.n as 'rows' FROM minions as m \n" +
                        "LEFT JOIN (SELECT id,count(name) as n FROM minions) as c\n" +
                        "on m.id = c.id;",
                ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery();
        int n = 0;
        rs.next();
        int rows = rs.getInt("rows");

        while (n < rows/2){
            rs.first();
            rs.relative(n);
            System.out.println(rs.getString("name"));
            rs.last();
            rs.relative(-n);
            System.out.println(rs.getString("name"));
            n++;
        }
        if(rows%2!=0){
            rs.absolute(rows/2+1);
            System.out.println(rs.getString("name"));
        }
        connection.close();
    }
}
