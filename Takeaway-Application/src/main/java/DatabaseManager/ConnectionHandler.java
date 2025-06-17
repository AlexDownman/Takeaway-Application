package DatabaseManager;

import Constants.SQLConstants;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

/**
 * Handles the lifecycle and access to the ConnectionPool.
 * Provides static methods to initialize the pool, acquire and release connections,
 * and gracefully shut down all pooled connections.
 */
public class ConnectionHandler {
    private static ConnectionPool connectionPool;

    /**
     * Initializes the connection pool using the database file path specified in {@link SQLConstants}.
     * Checks if the database file exists before creating the pool.
     *
     * @throws DBOperationException If the database file does not exist or connection pool initialization fails.
     */
    public static void init() throws DBOperationException {
        File dbFile = new File(SQLConstants.DATABASE_ROOT_PATH);
        if (!dbFile.exists()) {
            throw new DBOperationException(new IOException("Database file does not exist"));
        }

        String dbPath = dbFile.getAbsolutePath();
        String completeURL = SQLConstants.DATABASE_URL + dbPath;

        connectionPool = new ConnectionPool(completeURL, "", "", 5); // Pool size 5
        System.out.println("Connection pool initialized");
    }

    /**
     * Retrieves a connection from the connection pool.
     *
     * @return A {@link Connection} object to interact with the database.
     * @throws DBOperationException If the connection pool is not initialized or a connection cannot be retrieved.
     * @throws NullPointerException If {@link #init()} has not been called before this method.
     */
    public static Connection getConnection() throws DBOperationException {
        if (connectionPool == null) {
            throw new NullPointerException("Connection pool not initialized");
        }
        return connectionPool.getConnection();
    }

    /**
     * Returns a database connection back to the connection pool.
     *
     * @param connection The {@link Connection} to release.
     * @throws DBOperationException If releasing the connection fails.
     */
    public static void releaseConnection(Connection connection) throws DBOperationException {
        if (connectionPool != null) {
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * Shuts down the connection pool by closing all active connections.
     *
     * @throws DBOperationException If shutting down the pool fails.
     */
    public static void shutdownPool() throws DBOperationException {
        if (connectionPool != null) {
            connectionPool.shutdown();
        }
    }
}
