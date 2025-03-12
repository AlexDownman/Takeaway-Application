package SQLConnectionHandler;

import SQLConnectionHandler.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;

//download DB Browser to view SQLite database at https://sqlitebrowser.org/blog/version-3-13-1-released/
//Or you can configure intelliJ data source

public class ConnectionHandler {

    private Connection connection;


    public ConnectionHandler() {

        String url = "jdbc:sqlite:Takeaway.db"; // Specify the database URL/Path

        try {
            //Attempt creating the connection
            connection = DriverManager.getConnection(url);

            System.out.println("Connection Successful");

        } catch (SQLException e) {

            System.out.println("Error Connecting to Database");

            e.printStackTrace();

        }

    }


    public Connection getConnection() {

        return connection;

    }


    public void closeConnection() {

        try {

            if (connection != null) {

                System.out.println("Connection Closed");

                connection.close();

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }
}
