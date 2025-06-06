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


    public static final int MINIMUM_COL_WIDTH = 15;

    /**
     * Creates an object which handles SQL connections to the database
     *
     * @throws IOException
     */
    public DatabaseHandler() throws IOException {
        super();
    }

    public void pullTable(String tableName) throws SQLException {
        if (this.connection == null || this.connection.isClosed()) {
            System.out.println("Connection To Database Doesn't Exist");
            return;
        }

        PreparedStatement ps = this.connection.prepareStatement("SELECT * FROM " + tableName);
        ResultSet rs = ps.executeQuery();

        int colCount = rs.getMetaData().getColumnCount();

        List<String[]> colData = new ArrayList<>();

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
    }

    public static void main(String[] args) throws IOException, SQLException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.pullTable("ItemTable");
    }
}
