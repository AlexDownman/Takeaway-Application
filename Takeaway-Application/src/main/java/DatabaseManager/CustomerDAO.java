package DatabaseManager;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO extends DatabaseHandler {
    private final Connection connection = this.getConnection();
    private final Statement statement = this.getConnection().createStatement();

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

    public String[] getAllCustomerNames() throws SQLException {
        // Use ArrayList to dynamically collect names
        List<String> namesList = new ArrayList<>();

        // Prepare the SQL query
        String customerNameSQL = "SELECT CustomerName FROM CustomerTable";

        // Use try-with-resources to properly handle resources
        try (Statement stmt = this.connection.createStatement();
             ResultSet rs = stmt.executeQuery(customerNameSQL)) {

            // Iterate through all results
            while (rs.next()) {
                namesList.add(rs.getString("CustomerName"));
            }
        }

        // Convert ArrayList to array and return
        return namesList.toArray(new String[0]);
    }

    public String[] getAllCustomerPhoneNums() throws SQLException {
        String getter_SQL = "SELECT PhoneNum FROM CustomerTable";
        List<String> phoneNumsList = new ArrayList<>();

        try (Statement stmt = this.connection.createStatement();
        ResultSet rs = stmt.executeQuery(getter_SQL)) {
            while (rs.next()) {
                phoneNumsList.add(rs.getString("PhoneNum"));
            }
        }
        return phoneNumsList.toArray(new String[0]);
    }

    public String[] getAllCustomerEmails() throws SQLException {
        String getter_SQL = "SELECT Email FROM CustomerTable";
        List<String> emailsList = new ArrayList<>();
        try (Statement stmt = this.connection.createStatement();
        ResultSet rs = stmt.executeQuery(getter_SQL)) {
            while (rs.next()) {
                emailsList.add(rs.getString("Email"));
            }
        }
        return emailsList.toArray(new String[0]);
    }

    public String[] getAllCustomerAddress() throws SQLException {
        String getter_SQL = "SELECT Address FROM CustomerTable";
        List<String> addressList = new ArrayList<>();
        try (Statement stmt = this.connection.createStatement();
        ResultSet rs = stmt.executeQuery(getter_SQL)) {
            while (rs.next()) {
                addressList.add(rs.getString("Address"));
            }
        }
        return addressList.toArray(new String[0]);
    }

    public String[] getAllCustomerPostcodes() throws SQLException {
        String getter_SQL = "SELECT Postcode FROM CustomerTable";
        List<String> postcodeList = new ArrayList<>();
        try (Statement stmt = this.connection.createStatement();
        ResultSet rs = stmt.executeQuery(getter_SQL)) {
            while (rs.next()) {
                postcodeList.add(rs.getString("Postcode"));
            }
        }
        return postcodeList.toArray(new String[0]);
    }

    public static void main(String[] args) throws IOException, SQLException {
        CustomerDAO cust = new CustomerDAO();

        String[] customerNames = cust.getAllCustomerNames();

        for (String customerName : customerNames) {
            System.out.println(customerName);
        }

        //cust.addCustomer("Shayon", "0740748321", "shayondharr@gmail.com", "20 Rainbow Crossing, Leeds", "CF33 4AF");
    }

}
