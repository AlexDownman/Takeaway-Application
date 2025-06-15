package DatabaseManager;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseHandler extends ConnectionHandler {

    /**
     * Creates an object which handles SQL connections to the database
     *
     * @throws IOException
     */
    public DatabaseHandler() throws IOException {
        super();
    }

    private boolean isConnectionClosed() throws SQLException {
        if (this.connection == null || this.connection.isClosed()) {
            System.out.println("Connection To Database Doesn't Exist");
            return true;
        } else {
            System.out.println("Connection Successful");
            return false;
        }
    }

    /**
     * Gets an entire table as an Arraylist
     * @param tableName String name of the table
     * @return Arraylist of strings
     * @throws SQLException SQL error
     */
    public ArrayList<String[]> pullTable(String tableName) throws SQLException {
        if (this.isConnectionClosed()) {
            return null;
        }

        PreparedStatement ps = this.connection.prepareStatement("SELECT * FROM " + tableName);
        ResultSet rs = ps.executeQuery();

        int colCount = rs.getMetaData().getColumnCount();

        ArrayList<String[]> colData = new ArrayList<>();

        while (rs.next()) {
            String[] colDataRow = new String[colCount];
            for (int i = 1; i <= colCount; i++) {
                colDataRow[i - 1] = rs.getString(i);
            }
            colData.add(colDataRow);
        }

        rs.close();
        ps.close();

        for (String[] colDataRow : colData) {
            for (String colName : colDataRow) {
                System.out.print(colName + " ");
            }
        }

        return colData;
    }

    public void createTable (String tableName, ArrayList<String> columnDef, ArrayList<String> tableConstraints) throws SQLException {
        if (this.isConnectionClosed()) {
            return;
        }

        if (tableName == null ||  tableName.trim().isEmpty()) {
            throw new IllegalArgumentException("Table Name cannot be null or empty");
        }

        ArrayList<String> validDefinitions = filterNonEmptyStrings(columnDef);

        if (validDefinitions.isEmpty()) {
            throw new IllegalArgumentException("Column Definitions cannot be null or empty");
        }



    }

    private ArrayList<String> filterValidTableConstraints(ArrayList<String> tableConstraints) {
        if (tableConstraints == null || tableConstraints.isEmpty()) {
            throw new IllegalArgumentException("Array List cannot be null or empty");
        }

        for (String tableConstraint : tableConstraints) {}

    }

    private ArrayList<String> filterNonEmptyStrings(ArrayList<String> arrayList) {
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

    public static void main(String[] args) throws IOException, SQLException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.pullTable("ItemTable");
    }
}
