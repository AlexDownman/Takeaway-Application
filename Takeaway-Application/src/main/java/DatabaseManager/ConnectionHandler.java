package DatabaseManager;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
        String url = SQLCommonConstants.DATABASE_URL + dbPath;

        //try creating connection
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Connection Successful to: " + dbPath);
        } catch (SQLException e) {
            System.out.println("Error Connecting to Database: " + e.getMessage());
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
        DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.pullTable("ItemTable"); // Pass table name without quotes
        dbHandler.closeConnection();
    }
}