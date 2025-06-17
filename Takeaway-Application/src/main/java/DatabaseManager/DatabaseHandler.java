package DatabaseManager;

import Constants.TableConstraints;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class DatabaseHandler {
    /**
     * Gets an entire table as an Arraylist
     * @param tableName String name of the table
     * @return Arraylist of strings
     * @throws DBOperationException IO || SQL error
     */
    public static ArrayList<String[]> pullTable(String tableName) throws DBOperationException {

        Connection connection = null;
        try {
            connection = ConnectionHandler.getConnection();

            String query = "SELECT * FROM " + tableName;
            try (PreparedStatement ps = connection.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {

                int colCount = rs.getMetaData().getColumnCount();
                ArrayList<String[]> colData = new ArrayList<>();

                while (rs.next()) {
                    String[] colDataRow = new String[colCount];
                    for (int i = 1; i <= colCount; i++) {
                        colDataRow[i - 1] = rs.getString(i);
                    }
                    colData.add(colDataRow);
                }

                return colData;
            }

        } catch (SQLException e) {
            throw new DBOperationException(e);
        } finally {
            if (connection != null) {
                ConnectionHandler.releaseConnection(connection);
            }
        }
    }

    /**
     * Creates a new table in the database
     * @param tableName : String name
     * @param columnDef : List of column definitions
     * @param tableConstraints : List of table constraints
     * @throws DBOperationException IO || SQL, Exception
     */
    public static void createTable(String tableName, ArrayList<String> columnDef, ArrayList<String> tableConstraints) throws DBOperationException {

        if (tableName == null || tableName.trim().isEmpty()) {
            throw new IllegalArgumentException("Table name cannot be null or empty");
        }

        ArrayList<String> validDefinitions = filterNonEmptyStrings(columnDef);
        if (validDefinitions.isEmpty()) {
            throw new IllegalArgumentException("Column definitions cannot be null or empty");
        }

        ArrayList<String> validConstraints = filterValidTableConstraints(tableConstraints);

        // Build CREATE TABLE SQL
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append(" (");

        // Add column definitions
        for (int i = 0; i < validDefinitions.size(); i++) {
            queryBuilder.append(validDefinitions.get(i));
            if (i < validDefinitions.size() - 1 || !validConstraints.isEmpty()) {
                queryBuilder.append(", ");
            }
        }

        // Add table constraints
        for (int i = 0; i < validConstraints.size(); i++) {
            queryBuilder.append(validConstraints.get(i));
            if (i < validConstraints.size() - 1) {
                queryBuilder.append(", ");
            }
        }

        queryBuilder.append(");");
        String finalQuery = queryBuilder.toString();

        // Execute SQL
        Connection connection = null;
        try {
            connection = ConnectionHandler.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(finalQuery)) {
                ps.execute();
                System.out.println("Table '" + tableName + "' created or already exists.");
            }
        } catch (SQLException e) {
            throw new DBOperationException(e);
        } finally {
            if (connection != null) {
                ConnectionHandler.releaseConnection(connection);
            }
        }
    }


    /**
     * Filter method to remove empty and invalid table constraints from user input
     * @param tableConstraints : User input constraints
     * @return Validated constraints
     */
    private static ArrayList<String> filterValidTableConstraints(ArrayList<String> tableConstraints) {

        if (tableConstraints.isEmpty()) {
            return new ArrayList<>();
        }

        Iterator<String> iterator = tableConstraints.iterator();

        while (iterator.hasNext()) {
            String constraintStr = iterator.next().trim();
            TableConstraints constraint = TableConstraints.fromString(constraintStr);
            if (constraint == null) {
                iterator.remove();
            }
        }
        return tableConstraints;
    }

    /**
     * Filters out empty strings from an arraylist
     * @param arrayList : User input arraylist
     * @return Arraylist with no empty elements or an empty list
     */
    private static ArrayList<String> filterNonEmptyStrings(ArrayList<String> arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            throw new IllegalArgumentException("Array List cannot be null or empty");
        }

        ArrayList<String> result = new ArrayList<>();
        for (String str : arrayList) {
            if (str != null && !str.trim().isEmpty()) {
                result.add(str);
            }
        }
        return result;
    }

    public static void main(String[] args) throws DBOperationException {
        ConnectionHandler.init();

        ArrayList<String[]> yeah = DatabaseHandler.pullTable("CustomerTable");

        assert yeah != null;
        for (String[] row : yeah) {
            for (String colName : row) {
                System.out.print(colName + " ");
            }
            System.out.println("\n");
        }
    }
}
