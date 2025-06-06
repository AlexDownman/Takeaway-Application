package DatabaseManager;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class to handle SQL Connections
 */

public class ConnectionHandler {

    protected Connection connection;

    /**
     * Creates an object which handles SQL connections to the database
     * @throws IOException
     */
    public ConnectionHandler() throws IOException {
        openConnection();
    }

    /**
     * Accessor for connection attribute
     * @return Connection type
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Method establishes the connection to the sqlite database
     * @throws IOException
     */
    public void openConnection() throws IOException {
        File dbFile = new File(SQLConstants.DATABASE_ROOT_PATH);

        if (!dbFile.exists()) {
            throw new IOException("Database file does not exist");
        }

        String dbPath = dbFile.getAbsolutePath();
        String completeURL = SQLConstants.DATABASE_URL + dbPath;

        try {
            connection = DriverManager.getConnection(completeURL);
            System.out.println("Database connection established");
        } catch (SQLException e) {
            System.out.println("Database connection failed\n" + e.getMessage());
        }
    }

    /**
     * Method closes the Connection database
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Connection closed");
                this.connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Connection close failed\n" + e.getMessage());
        }
    }
}
