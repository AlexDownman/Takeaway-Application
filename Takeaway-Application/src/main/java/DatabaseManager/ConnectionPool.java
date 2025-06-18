package DatabaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private final BlockingQueue<Connection> connectionPool;
    private final String url;
    private final String user;
    private final String password;
    private final int poolSize;

    /**
     * Instantiate a connection pool
     * @param url : Dont need since its SQLite
     * @param user : ^
     * @param password : ^
     * @param poolSize : Maximum Number of connections
     * @throws DBOperationException : SQL || I/O Exception
     */
    public ConnectionPool(String url, String user, String password, int poolSize) throws DBOperationException {
        this.url = url;
        this.user = user;
        this.password = password;
        this.poolSize = poolSize;
        this.connectionPool = new ArrayBlockingQueue<>(poolSize);
        initializeConnectionPool();
    }

    /**
     * Fills the connection pool with connections
     * @throws DBOperationException : SQL || I/O Exception
     */
    private void initializeConnectionPool() throws DBOperationException {
        for  (int i = 0; i < poolSize; i++) {
            connectionPool.add(createNewConnection());
        }
    }

    /**
     * Creates a new connection instance
     * @return Connection instance
     * @throws DBOperationException : SQL || I/O Exception
     */
    private Connection createNewConnection() throws DBOperationException {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new DBOperationException(e);
        }
    }

    /**
     * Takes a connection from the pool
     * @return : Connection
     * @throws DBOperationException : SQL || I/O Exception
     */
    public Connection getConnection() throws DBOperationException {
        try {
            Connection connection = connectionPool.take();

            if (!isConnectionValid(connection)) {
                connection = createNewConnection();
            }

            return connection;
        } catch (Exception e) {
            throw new DBOperationException(e);
        }
    }

    /**
     * Returns a connection to the pool
     * @param connection : Connection to return
     * @throws DBOperationException : SQL || I/O Exception
     */
    public void releaseConnection(Connection connection) throws DBOperationException {
        try {
            if (connection != null) {
                connectionPool.offer(connection);
            }
        } catch (Exception e) {
            throw new DBOperationException(e);
        }
    }

    /**
     * Closes all connections
     * @throws DBOperationException : SQL || I/O Exception
     */
    public void shutdown() throws DBOperationException {
        try {
            for (Connection connection : connectionPool) {
                connection.close();
            }
        } catch (Exception e) {
            throw new DBOperationException(e);
        }
    }

    //So simple im not gonna javadoc it
    private boolean isConnectionValid(Connection connection) throws DBOperationException {
        try {
            return connection != null && connection.isValid(2);
        } catch (Exception e) {
            throw new DBOperationException(e);
        }
    }
}
