import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    private static String query;
    private static Connection connection;
    private static final String url = "jdbc:mysql://localhost:3306/%s?useSSL=false";
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws SQLException, IOException {
        Properties prop = new Properties();//set your DB username and password here
        prop.setProperty("user", "root");
        prop.setProperty("password", "InTheClub454494");

        connection = DriverManager.getConnection(String.format(url, "minions_db"), prop);
        //You need to crate a new minions_db after every CRUD operation or else you'll mess up the database
        // and won't get the desired outputs on the other tasks

//        getVillainsByMinionsCount();//2.Get Villains’ Names
//        getMinionNamesById();//3.Get Minion Names
//        addMinion();//4.Add Minion
//        changeCasing();//5.Change Town Names Casing
//        removeVillain();//6.Remove Villain
//        getAllMinionNamesShuffled();//7.Print All Minion Names
//        increaseMinionsAge();//8.Increase Minions Age
        increaseAgeById();//9.Increase Age Stored Procedure

        connection.close();
        //Don't forget to add the Connector/J library if you have Workbench on your machine it should be in
        //C:\Program Files (x86)\MySQL\Connector J 8.0\mysql-connector-java-8.0.19.jar
    }

    private static boolean checkIfExists(int value, String tableName) throws SQLException {
        query = String.format("SELECT * FROM %s WHERE id = '%s';", tableName, value);

        return getResultSet(query).next();
    }

    private static boolean checkByName(String value, String tableName) throws SQLException {
        query = "SELECT * FROM " + tableName + " WHERE name = ?;";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, value);

        return statement.executeQuery().next();
    }

    private static ResultSet getResultSet(String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);

        return statement.executeQuery();
    }

    private static Integer getUpdatedCount(String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);

        return statement.executeUpdate();
    }

    private static void getSeparatorRow() {
        System.out.printf("%n---------------------------------------%n%n");
    }

    private static void increaseAgeById() throws SQLException, IOException {
        System.out.println("Enter minion id:");
        int id = Integer.parseInt(reader.readLine());

        try {
            getUpdatedCount(
                    "CREATE PROCEDURE usp_get_older(value INT)\n" +
                            "BEGIN\n" +
                            "UPDATE minions\n" +
                            "SET age = age + 1\n" +
                            "WHERE id = value;\n" +
                            "END ");
        } catch (Exception e) {
            System.out.println("Procedure already exists.");
        }

        CallableStatement statement = connection.prepareCall("CALL usp_get_older(?)");
        statement.setInt(1, id);
        statement.execute();

        ResultSet set = getResultSet("SELECT name,age FROM minions WHERE id=" + id);
        System.out.println(set.next() ? String.format("|%10.10s| %d |%n", set.getString("name"), set.getInt("age")) : "No minion with that id...");
        getSeparatorRow();
    }

    private static void increaseMinionsAge() throws IOException, SQLException {
        System.out.println("Enter id list:");
        String arr = reader.readLine().trim().replaceAll("\\s+", ",");

        getUpdatedCount(String.format("UPDATE minions SET age = age + 1, name = LOWER(name) WHERE id in (%s)", arr));
        ResultSet set = getResultSet("SELECT name,age FROM minions");

        while (set.next()) {
            System.out.printf("| %11.11s | %d |%n", set.getString("name"), set.getInt("age"));
        }
        getSeparatorRow();
    }

    private static void changeCasing() throws SQLException, IOException {
        System.out.println("Enter country name:");
        query = "UPDATE towns\n" +
                "SET name = UPPER(name)\n" +
                "WHERE LOWER(country)=LOWER(?);";

        PreparedStatement statement = connection.prepareStatement(query);
        String country = reader.readLine();
        statement.setString(1, country);

        int rows = statement.executeUpdate();

        if (rows > 0) {
            System.out.println(rows + " town names were affected.");
            List<String> arr = new ArrayList<>();

            ResultSet set = getResultSet(String.format("SELECT name FROM towns WHERE LOWER(country) = LOWER('%s')", country));
            while (set.next()) {
                arr.add(set.getString("name"));
            }
            System.out.println("[" + String.join(", ", arr) + "]");
        } else {
            System.out.println("No town names were affected.");
        }

        getSeparatorRow();
    }

    private static void removeVillain() throws IOException, SQLException {
        System.out.println("Please enter villain id:");
        int id = Integer.parseInt(reader.readLine());

        if (!checkIfExists(id, "villains")) {
            System.out.println("No such villain was found");
            return;
        }
        int count = getUpdatedCount("DELETE FROM minions_villains WHERE villain_id = " + id);

        System.out.printf("%s was deleted%n", getVillainName(id));
        getUpdatedCount("DELETE FROM villains WHERE id = " + id);
        System.out.printf("%d minions released%n", count);

        getSeparatorRow();
    }

    private static String[] splitLineBy(String reg) throws IOException {
        return reader.readLine().split(reg);
    }

    private static void getAllMinionNamesShuffled() throws SQLException {
        System.out.println("Shuffled minions:");
        List<String> arr = new ArrayList<>();
        query = "SELECT name FROM minions";
        ResultSet set = getResultSet(query);

        while (set.next()) {
            arr.add(set.getString(1));
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.size() / 2; i++) {
            builder.append(arr.get(i)).append(System.lineSeparator());
            builder.append(arr.get((arr.size() - 1) - i)).append(System.lineSeparator());
        }
        System.out.print(builder);
        getSeparatorRow();
    }

    private static void addMinion() throws IOException, SQLException {
        System.out.println("Enter minion and villain parameters:");
        String[] minionData = splitLineBy("\\s+");
        String villainName = splitLineBy("\\s+")[1];

        insertMinion(minionData);
        insertVillain(minionData[1], villainName);
        getSeparatorRow();
    }

    private static void insertVillain(String name, String villain) throws SQLException {
        if (!checkByName(villain, "villains")) {
            query = String.format("INSERT INTO villains(name,evilness_factor) VALUES ('%s','evil');", villain);

            if (getUpdatedCount(query) == 1) {
                System.out.printf("Villain %s was added to the database.%n", villain);
            } else {
                System.out.println("Something went wrong!Could not add this villain");
            }
        }
        query = String.format("INSERT INTO minions_villains(minion_id,villain_id) VALUES (%d,%d);", getId("minions", name), getId("villains", villain));
        if (getUpdatedCount(query) != 1) {
            System.out.println("Something went wrong!Could not add minion");
        } else {
            System.out.printf("Successfully added %s to be minion of %s.%n", name, villain);
        }
    }

    private static void insertMinion(String[] data) throws SQLException {
        if (!checkByName(data[3], "towns")) {
            query = String.format("INSERT INTO towns (name) VALUES ('%s');", data[3]);

            if (getUpdatedCount(query) == 1) {
                System.out.printf("Town %s was added to the database.%n", data[3]);
            } else {
                System.out.println("Something went wrong!Could not add town");
            }
        }
        query = String.format("INSERT INTO minions (name,age,town_id) VALUES ('%s',%s,%d);", data[1], data[2], getId("towns", data[3]));
        if (getUpdatedCount(query) != 1) {
            System.out.println("Something went wrong!Could not add minion");
        }
    }

    private static Integer getId(String table, String value) throws SQLException {
        query = String.format("SELECT id FROM %s WHERE name = '%s';", table, value);
        ResultSet set = getResultSet(query);

        return set.next() ? set.getInt("id") : null;
    }

    private static void getVillainsByMinionsCount() throws SQLException, IOException {
        System.out.println("Enter desired number of minions:");
        query = String.format("SELECT v.name,COUNT(mv.minion_id) AS count FROM villains AS v\n" +
                "JOIN minions_villains AS mv\n" +
                "ON v.id = mv.villain_id\n" +
                "GROUP BY v.name\n" +
                "HAVING count>%d\n" +
                "ORDER BY count DESC;", Integer.parseInt(reader.readLine()));

        ResultSet set = getResultSet(query);

        while (set.next()) {
            System.out.printf("| %-8s | %d |%n",
                    set.getString("name"),
                    set.getInt("count"));
        }
        getSeparatorRow();
    }

    private static void getMinionNamesById() throws SQLException, IOException {
        System.out.println("Enter Villain Id:");
        int id = Integer.parseInt(reader.readLine());

        if (!checkIfExists(id, "villains")) {
            System.out.printf("No villain with ID %d exists in the database.", id);
            return;
        }
        System.out.printf("Villain: %s%n", getVillainName(id));
        printMinions(id);
        getSeparatorRow();
    }

    private static String getVillainName(int id) throws SQLException {
        query = String.format("SELECT name FROM villains WHERE id = %d", id);
        ResultSet set = getResultSet(query);

        return set.next() ? set.getString("name") : "sorry not found";
    }

    private static void printMinions(int id) throws SQLException {
        query = String.format("SELECT m.name,m.age FROM minions AS m\n" +
                "JOIN minions_villains AS mv\n" +
                "ON m.id = mv.minion_id\n" +
                "WHERE mv.villain_id=%d;", id);

        ResultSet set = getResultSet(query);
        int index = 0;

        while (set.next()) {
            System.out.printf("%d. %s %d%n",
                    ++index,
                    set.getString("name"),
                    set.getInt("age"));
        }
    }
}