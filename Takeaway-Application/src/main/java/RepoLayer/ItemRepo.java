package RepoLayer;

import DatabaseManager.ConnectionHandler;
import DatabaseManager.DBOperationException;
import EntityClasses.Item;
import RepoLayer.RepoInterfaces.ItemInterface;
import CacheLayer.Cache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static Constants.SQLConstants.UNIQUE_ERROR_CODE;

public class ItemRepo implements ItemInterface {

    @Override
    public void addItem(Item item) throws Exception {
        String query = "INSERT INTO ItemTable (ItemName, ItemQuantity, ItemPrice) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, item.getItemName());
            stmt.setString(2, "".trim() + item.getItemQuantity());
            stmt.setString(3, item.getItemPrice());
            stmt.executeUpdate();

            Cache.addOrUpdateItem(item);
        } catch (SQLException ex) {
            if (UNIQUE_ERROR_CODE.equals(ex.getSQLState()) && ex.getMessage().contains("UNIQUE constraint failed")) {
                throw new DBOperationException(ex);
            }
            throw new DBOperationException(ex);
        }
    }

    @Override
    public Item getItemById(int id) throws Exception {
        String query = "SELECT * FROM Item WHERE ItemID = ?";
        try (Connection conn = ConnectionHandler.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Item(
                        rs.getInt("ItemID"),
                        rs.getString("ItemName"),
                        rs.getInt("ItemQuantity"),
                        rs.getString("ItemPrice")
                );
            }
            return null;
        } catch (Exception e) {
            throw new DBOperationException(e);
        }
    }

    @Override
    public List<Item> getAllItems() throws Exception {
        String query =  "SELECT * FROM Item";
        List<Item> items = new ArrayList<>();

        try (Connection conn = ConnectionHandler.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                items.add(new Item(
                        rs.getInt("ItemID"),
                        rs.getString("ItemName"),
                        rs.getInt("ItemQuantity"),
                        rs.getString("ItemPrice")
                ));
            }
            return items;
        } catch (Exception e) {
            throw new DBOperationException(e);
        }
    }

    @Override
    public void updateItem(Item item) throws Exception {
        String query = "UPDATE Item SET ItemName = ?, ItemQuantity = ?, ItemPrice = ? WHERE ItemID = ?";

        try (Connection conn = ConnectionHandler.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, item.getItemName());
            stmt.setInt(2, item.getItemQuantity());
            stmt.setString(3, item.getItemPrice());
            stmt.setInt(4, item.getItemID());
            stmt.executeUpdate();

            Cache.addOrUpdateItem(item);

        } catch (Exception ex) {
            throw new DBOperationException(ex);
        }
    }

    @Override
    public void deleteItem(int id) throws Exception {
        String query = "DELETE FROM Item WHERE ItemID = ?";
        try (Connection conn = ConnectionHandler.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();

            Cache.deleteItem(id);

        } catch (Exception ex) {
            throw new DBOperationException(ex);
        }
    }
}
