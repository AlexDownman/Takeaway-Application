package EntityClasses;

public class Item {
    private int ItemID;
    private String ItemName;
    private int ItemQuantity;
    private String ItemPrice;

    public Item(int ItemID, String ItemName, int ItemQuantity, String ItemPrice) {
        this.ItemID = ItemID;
        this.ItemName = ItemName;
        this.ItemQuantity = ItemQuantity;
        this.ItemPrice = ItemPrice;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public int getItemQuantity() {
        return ItemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        ItemQuantity = itemQuantity;
    }

    public String getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(String itemPrice) {
        ItemPrice = itemPrice;
    }
}
