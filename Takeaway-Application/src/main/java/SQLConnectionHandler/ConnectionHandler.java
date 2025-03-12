package SQLConnectionHandler;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionHandler {

    private Connection connection;

    public ConnectionHandler() throws IOException {
        // Check for Takeaway.db in the current directory only
        File dbFile = new File("Takeaway-Application/src/Takeaway.db");

        //if file doesnt exist
        if (!dbFile.exists()) {
            throw new IOException("Database file 'Takeaway.db' not found in current directory: " +
                    new File(".").getAbsolutePath());
        }

        //create db path
        String dbPath = dbFile.getAbsolutePath();
        String url = "jdbc:sqlite:" + dbPath;

        //try creating connection
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Connection Successful to: " + dbPath);
        } catch (SQLException e) {
            System.out.println("Error Connecting to Database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Pulls and displays data from the specified table.
     * @param tableName The name of the table (e.g., "ItemTable")
     */
    public void pullTable(String tableName) {
        try {
            if (connection == null || connection.isClosed()) {
                System.out.println("Connection Failed");
                return;
            }

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);

            // Print column names for debugging
            int columnCount = rs.getMetaData().getColumnCount();
            System.out.println("Columns in " + tableName + ":");
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rs.getMetaData().getColumnName(i) + " (" +
                        rs.getMetaData().getColumnTypeName(i) + ") ");
            }
            System.out.println();

            // Print rows
            while (rs.next()) {
                System.out.println(rs.getString(1)); // Adjust based on your table structure
            }

            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Connection Closed");
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ConnectionHandler handler = new ConnectionHandler();
        handler.pullTable("ItemTable"); // Pass table name without quotes
        handler.closeConnection();
    }
}