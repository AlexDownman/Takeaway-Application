package Tablehandlers;

import DatabaseManager.DatabaseConnection;
import DatabaseManager.DatabaseHandler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO extends DatabaseHandler implements TableHandlerInterface {
    private final Connection connection;

    public CustomerDAO() throws IOException {
        super();
        this.connection = getConnection();
    }

    /**
     * Executes a query to retrieve a single column from the CustomerTable.
     *
     * @param columnName the name of the column to retrieve
     * @return an array of values from the specified column
     * @throws SQLException if a database error occurs
     */
    private String[] fetchColumnValues(String columnName) throws SQLException {
        List<String> valuesList = new ArrayList<>();
        String sql = "SELECT " + columnName + " FROM CustomerTable";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                valuesList.add(rs.getString(columnName));
            }
        }
        return valuesList.toArray(new String[0]);
    }

    public String[] getAllCustomerNames() throws SQLException {
        return fetchColumnValues("CustomerName");
    }

    public String[] getAllCustomerPhoneNums() throws SQLException {
        return fetchColumnValues("Phone");
    }

    public String[] getAllCustomerEmails() throws SQLException {
        return fetchColumnValues("Email");
    }

    public String[] getAllCustomerAddress() throws SQLException {
        return fetchColumnValues("Address");
    }

    public String[] getAllCustomerPostcodes() throws SQLException {
        return fetchColumnValues("Postcode");
    }

    /**
     * Adds a customer to the database after validation and normalization.
     *
     * @param customer the customer to add
     * @throws DatabaseConnection if the database connection fails or input is invalid
     */
    public void addCustomer(Customer customer) throws DatabaseConnection {
        if (connection == null) {
            throw new DatabaseConnection("Database connection does not exist.");
        }

        if (!customer.isValid()) {
            throw new DatabaseConnection("Invalid customer data: " + customer);
        }

        String sql = "INSERT INTO CustomerTable (CustomerName, PhoneNum, Email, Address, Postcode) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getPhone()); // Already normalized
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getAddress());
            pstmt.setString(5, customer.getPostcode());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnection("Failed to add customer: " + e.getMessage());
        }
    }

    /**
     * Retrieves all customers from the database.
     *
     * @return a list of all customers
     * @throws SQLException if a database error occurs
     */
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT CustomerName, PhoneNum, Email, Address, Postcode FROM CustomerTable";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                customers.add(new Customer(
                        rs.getString("CustomerName"),
                        rs.getString("PhoneNum"),
                        rs.getString("Email"),
                        rs.getString("Address"),
                        rs.getString("Postcode")
                ));
            }
        }
        return customers;
    }

    @Override
    public void updateTable() {
        // TODO: Implement update logic
    }

    @Override
    public void deleteTable() {
        // TODO: Implement delete logic
    }

    @Override
    public void insertTable(CustomerTableColumns[] columns, String... valuesArray) {
        // TODO: Implement insert logic
    }

    @Override
    public void readTable(String searchValue) {
        // TODO: Implement search logic
    }

    @Override
    public void readAllTable() {
        // TODO: Implement read all logic
    }

    public static void main(String[] args) throws IOException {
        try {
            CustomerDAO dao = new CustomerDAO();

            List<Customer> customers = dao.getAllCustomers();
            System.out.println(customers.get(0).getPhone()); // Outputs: 0740748321
            for (Customer customer : customers) {
                System.out.println(customer);
            }

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }
}