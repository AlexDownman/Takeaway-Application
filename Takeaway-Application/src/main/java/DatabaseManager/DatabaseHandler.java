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
            }
            System.out.println();

            // Print rows
            while (rs.next()) {
                System.out.printf("%s %s %s %s\n", rs.getString(1), rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)); // Adjust based on your table structure
            }

            rs.close();
            pStatement.close();
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    /**
     * Creates a new SQLite table based on the provided table name, column definitions, and table constraints.
     *
     * @param tableName         The name of the table to create.
     * @param columnDefinitions A list of column definitions (e.g., "id INTEGER PRIMARY KEY").
     * @param tableConstraints  A list of table-level constraints (e.g., "UNIQUE (name, age)").
     */
    public void createTable(String tableName, List<String> columnDefinitions, List<String> tableConstraints) {
        // Validate table name
        if (tableName == null || tableName.trim().isEmpty()) {
            throw new IllegalArgumentException("Table name cannot be null or empty");
        }

        // Validate and filter column definitions
        List<String> validColumns = filterNonEmpty(columnDefinitions);
        if (validColumns.isEmpty()) {
            throw new IllegalArgumentException("At least one valid column definition is required");
        }

        // Filter table constraints (optional, so null is allowed)
        List<String> validConstraints = filterNonEmpty(tableConstraints);

        // Combine column definitions and table constraints
        List<String> allDefinitions = new ArrayList<>(validColumns);
        allDefinitions.addAll(validConstraints);

        // Construct the SQL statement
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + String.join(", ", allDefinitions) + ");";

        // Execute the SQL statement
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table '" + tableName + "' created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    /**
     * Filters out null or empty strings from the provided list and trims the remaining strings.
     *
     * @param list The list of strings to filter.
     * @return A new list containing only non-empty trimmed strings.
     */
    private List<String> filterNonEmpty(List<String> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        List<String> result = new ArrayList<>();
        for (String s : list) {
            if (s != null && !s.trim().isEmpty()) {
                result.add(s.trim());
            }
        }
        return result;
    }

    public void closeConnection() {
        connectionHandler.closeConnection();
    }
}
