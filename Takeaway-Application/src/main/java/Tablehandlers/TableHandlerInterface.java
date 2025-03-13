package Tablehandlers;

import java.sql.SQLException;

// This class defines what all DAO classes should have at the very least
public interface TableHandlerInterface {
    void updateTable() throws SQLException;
    void deleteTable() throws SQLException;
    void insertTable(CustomerTableColumns[] Columns, String... ValuesArray) throws SQLException;
    void readTable(String searchValue) throws SQLException;
    void readAllTable() throws SQLException;
}
