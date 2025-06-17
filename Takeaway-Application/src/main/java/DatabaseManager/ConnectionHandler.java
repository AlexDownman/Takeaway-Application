package DatabaseManager;

import Constants.SQLConstants;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class to handle SQL Connections
 */

public class ConnectionHandler {

    public static Connection connection;

    /**
     * Accessor for connection attribute. Don't need this since it's a public global variable
     * @return Connection
     */
    public static Connection getConnection() {
        return ConnectionHandler.connection;
    }

    /**
     * Method establishes the connection to the sqlite database
     * @throws DBOperationException : IO || SQL, Exception
     */
    public static void openConnection() throws DBOperationException {
        File dbFile = new File(SQLConstants.DATABASE_ROOT_PATH);

        if (!dbFile.exists()) {
            throw new DBOperationException(new IOException("Database file does not exist"));
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
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Connection closed");
                ConnectionHandler.connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Connection close failed\n" + e.getMessage());
        }
    }

    /**
     * Checks whether the SQL connection is closed.
     * @return boolean
     * @throws DBOperationException : IO || SQL, Exception
     */
    public static boolean isConnectionClosed() throws DBOperationException {
        try {
            if (ConnectionHandler.connection == null || ConnectionHandler.connection.isClosed()) {
                System.out.println("Connection To Database Doesn't Exist");
                return true;
            } else {
                System.out.println("Connection Successful");
                return false;
            }
        } catch (SQLException e) {
            throw new DBOperationException(e);
        }
    }
}
