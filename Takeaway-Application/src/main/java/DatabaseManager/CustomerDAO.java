package DatabaseManager;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class CustomerDAO extends DatabaseHandler {

    private Statement statement = this.getConnection().createStatement();

    public CustomerDAO() throws IOException, SQLException {
        super();
    }

    public void addCustomer(String name, String phone, String email, String address, String postcode) throws DatabaseConnection {
        String sql = "INSERT INTO CustomerTable (CustomerName, PhoneNum, email, address, postcode) VALUES (?, ?, ?, ?, ?)";

        try {
            if (this.getConnection() == null) {
                throw new DatabaseConnection("Database connection does not exist.");
            }
            if (name == null || name.isEmpty()) {
                throw new DatabaseConnection("Customer name cannot be empty");
            }
            if (phone == null || phone.isEmpty()) {
                throw new DatabaseConnection("Customer phone cannot be empty");
            }
            if (email == null || email.isEmpty()) {
                throw new DatabaseConnection("Customer email cannot be empty");
            }
            if (address == null || address.isEmpty()) {
                throw new DatabaseConnection("Customer address cannot be empty");
            }
            if (postcode == null || postcode.isEmpty()) {
                throw new DatabaseConnection("Customer postcode cannot be empty");
            }

            PreparedStatement pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, email);
            pstmt.setString(4, address);
            pstmt.setString(5, postcode);
            pstmt.executeUpdate();

        } catch (DatabaseConnection e) {
            throw new DatabaseConnection(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public ResultSet getAllCustomerNames() throws SQLException {
        String customerNameSQL = "SELECT CustomerName FROM CustomerTable";
        return statement.executeQuery(customerNameSQL);
    }

    public ResultSet getAllCustomerPhoneNums() throws SQLException {
        String getter_SQL = "SELECT PhoneNum FROM CustomerTable";
        return statement.executeQuery(getter_SQL);
    }

    public ResultSet getAllCustomerEmails() throws SQLException {
        String getter_SQL = "SELECT Email FROM CustomerTable";
        return statement.executeQuery(getter_SQL);
    }

    public ResultSet getAllCustomerAddress() throws SQLException {
        String getter_SQL = "SELECT Address FROM CustomerTable";
        return statement.executeQuery(getter_SQL);
    }

    public ResultSet getAllCustomerPostcodes() throws SQLException {
        String getter_SQL = "SELECT Postcode FROM CustomerTable";
        return statement.executeQuery(getter_SQL);
    }

    public static void main(String[] args) throws IOException, SQLException {
        CustomerDAO cust = new CustomerDAO();

        ResultSet customerNames = cust.getAllCustomerNames();

        while (customerNames.next()) {
            System.out.println(customerNames.getString("CustomerName"));
        }

        //cust.addCustomer("Shayon", "0740748321", "shayondharr@gmail.com", "20 Rainbow Crossing, Leeds", "CF33 4AF");
    }

}
