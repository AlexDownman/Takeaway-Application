package SQLConnectionHandler;

import SQLConnectionHandler.*;
import javafx.scene.chart.PieChart;

import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.sql.DatabaseMetaData;

//download DB Browser to view SQLite database at https://sqlitebrowser.org/blog/version-3-13-1-released/
//Or you can configure intelliJ data source

public class ConnectionHandler {

    private Connection connection;


    public ConnectionHandler() {

        String relativePath = "C:/Users/Teoman/Desktop/Takeaway-Application/Takeaway-Application/src/Takeaway.db";

        String url = "jdbc:sqlite:" + relativePath; // Specify the database URL/Path

        try {
            //Attempt creating the connection
            connection = DriverManager.getConnection(url);
            System.out.println("Connection Successful");

        } catch (SQLException e) {
            System.out.println("Error Connecting to Database");
            e.printStackTrace();
        }

    }

    public void pullTable(String tableName) {
        try {
            if (connection == null || connection.isClosed()) {
                System.out.println("Connection Failed");
                return;
            }

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);

            // Check if the ResultSet is empty
            if (!rs.isBeforeFirst()) {
                System.out.println("Table '" + tableName + "' exists but contains no data.");
                rs.close();
                statement.close();
                return;
            }

            // Print column names for debugging
            int columnCount = rs.getMetaData().getColumnCount();
            System.out.println("Columns in " + tableName + ":");
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rs.getMetaData().getColumnName(i) + " (" + rs.getMetaData().getColumnTypeName(i) + ") ");
            }
            System.out.println();

            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            rs.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("Error Connecting to Databaseee");
            e.printStackTrace();
        }
    }


    public Connection getConnection() {
        return connection;
    }


    public void closeConnection() {

        try {

            if (connection != null || !connection.isClosed()) {

                System.out.println("Connection Closed");

                connection.close();

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public static void main(String[] args) {
        ConnectionHandler handler = new ConnectionHandler();
        handler.pullTable("ItemTable"); // Fetch data from ItemTable
        handler.closeConnection();
    }
}
