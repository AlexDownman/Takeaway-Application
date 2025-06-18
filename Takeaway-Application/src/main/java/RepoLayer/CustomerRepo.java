package RepoLayer;

import DatabaseManager.ConnectionHandler;
import DatabaseManager.DBOperationException;
import EntityClasses.Customer;
import RepoLayer.RepoInterfaces.CustomerInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepo implements CustomerInterface {

    public static final String UNIQUE_ERROR_CODE = "23000";

    @Override
    public void addCustomer(Customer user) throws Exception {
        String query = "INSERT INTO CustomerTable (CustomerName, PhoneNum, Email, Address, Postcode) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPhoneNum());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getAddress());
            stmt.setString(5, user.getPostCode());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            if (UNIQUE_ERROR_CODE.equals(ex.getSQLState()) && ex.getMessage().contains("UNIQUE constraint failed")) {
                throw new DBOperationException(ex);
            }
            throw new DBOperationException(ex);
        }
    }


    @Override
    public Customer getCustomerById(int id) throws Exception {
        String sql = "SELECT * FROM CustomerTable WHERE CustomerID = ?";
        try (Connection conn = ConnectionHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Customer(
                        rs.getInt("CustomerID"),
                        rs.getString("CustomerName"),
                        rs.getString("PhoneNum"),
                        rs.getString("Email"),
                        rs.getString("Address"),
                        rs.getString("Postcode")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new DBOperationException(e);
        }
    }

    @Override
    public List<Customer> getAllCustomers() throws Exception {
        String sql = "SELECT * FROM CustomerTable";
        List<Customer> customers = new ArrayList<>();

        try (Connection conn = ConnectionHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                customers.add(new Customer(
                        rs.getInt("CustomerID"),
                        rs.getString("CustomerName"),
                        rs.getString("PhoneNum"),
                        rs.getString("Email"),
                        rs.getString("Address"),
                        rs.getString("Postcode")
                ));
            }

            return customers;
        } catch (SQLException e) {
            throw new DBOperationException(e);
        }
    }

    @Override
    public void updateCustomer(Customer user) throws Exception {
        String sql = "UPDATE CustomerTable SET CustomerName = ?, PhoneNum = ?, Email = ?, Address = ?, Postcode = ? WHERE CustomerID = ?";
        try (Connection conn = ConnectionHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPhoneNum());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getAddress());
            stmt.setString(5, user.getPostCode());
            stmt.setInt(6, user.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DBOperationException(e);
        }
    }

    @Override
    public void deleteCustomer(int id) throws DBOperationException {
        String sql = "DELETE FROM CustomerTable WHERE CustomerID = ?";
        try (Connection conn = ConnectionHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DBOperationException(e);
        }
    }
}
