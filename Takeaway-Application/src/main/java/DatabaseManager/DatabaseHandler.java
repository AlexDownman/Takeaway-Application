package DatabaseManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private ConnectionHandler connectionHandler;
    private Connection connection;

    public DatabaseHandler() {
        try {
            this.connectionHandler = new ConnectionHandler();
            this.connection = connectionHandler.getConnection();
        } catch (IOException e) {
            System.out.println(e.getMessage());
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

            PreparedStatement pStatement = connection.prepareStatement("SELECT * FROM " + tableName);
            ResultSet rs = pStatement.executeQuery();

            // Print column names for debugging
            int columnCount = rs.getMetaData().getColumnCount();
            System.out.println("Columns in " + tableName + ":");
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rs.getMetaData().getColumnName(i) + " (" +
                        rs.getMetaData().getColumnTypeName(i) + ") ");

                System.out.printf("%-10s %-5s", rs.getMetaData().getColumnName(i), rs.getMetaData().getColumnTypeName(i));
            }
            System.out.println();

            // Print rows
            while (rs.next()) {
                System.out.printf("%-10s %-10s %-10s %-10s\n", rs.getString(1), rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)); // Adjust based on your table structure
            }

            rs.close();
            pStatement.close();
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    public void closeConnection() {
        connectionHandler.closeConnection();
    }
}
