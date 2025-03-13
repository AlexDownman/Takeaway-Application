package DatabaseManager;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHandler {

    private Connection connection;

    public ConnectionHandler() throws IOException {
        //check for Takeaway.db in the current directory only
        File dbFile = new File("Takeaway-Application/src/Takeaway.db");

        //if file doesn't exist
        if (!dbFile.exists()) {
            throw new IOException("Database file 'Takeaway.db' not found in current directory: " +
                    new File(".").getAbsolutePath());
        }

        //create the database path
        String dbPath = dbFile.getAbsolutePath();
        String url = "jdbc:sqlite:" + dbPath;

        //try creating the connection
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Connection Successful to: " + dbPath);
        } catch (SQLException e) {
            System.out.println("Error Connecting to Database: " + e.getMessage());
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
            System.out.println("Error Disconnecting to Database: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.pullTable("ItemTable");
        dbHandler.closeConnection();
    }
}
