package DatabaseManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends ConnectionHandler {
    private ConnectionHandler connectionHandler;
    private Connection connection;

    public DatabaseHandler() throws IOException {
        super();
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

            // Get metadata for column information
            int columnCount = rs.getMetaData().getColumnCount();
            List<String> columnNames = new ArrayList<>();
            List<Integer> columnWidths = new ArrayList<>();

            // Calculate maximum width for each column
            for (int i = 1; i <= columnCount; i++) {
                String colName = rs.getMetaData().getColumnName(i);
                columnNames.add(colName);
                columnWidths.add(Math.max(colName.length(), 15)); // Minimum width of 15
            }

            // Print table header
            printDivider(columnWidths);
            printRow(columnNames, columnWidths, "|");
            printDivider(columnWidths);

            // Print rows
            while (rs.next()) {
                List<String> rowData = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    String value = rs.getString(i);
                    rowData.add(value != null ? value : "NULL");
                }
                printRow(rowData, columnWidths, "|");
            }
            printDivider(columnWidths);

            rs.close();
            pStatement.close();
        } catch (SQLException e) {
            // Handle any SQL exceptions that might occur
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

    /**
     * Ends the connection.
     */
    public void closeConnection() {
        connectionHandler.closeConnection();
    }

    /**
     * Prints a formatted row with specified widths and separator.
     */
    private void printRow(List<String> data, List<Integer> widths, String separator) {
        for (int i = 0; i < data.size(); i++) {
            System.out.printf("%s %-" + widths.get(i) + "s ", separator, data.get(i));
        }
        System.out.println(separator);
    }

    /**
     * Prints a divider line based on column widths.
     */
    private void printDivider(List<Integer> widths) {
        for (int width : widths) {
            System.out.print("+");
            for (int i = 0; i < width + 2; i++) { // +2 for padding
                System.out.print("-");
            }
        }
        System.out.println("+");
    }
}
