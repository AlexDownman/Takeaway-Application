package RepoLayer.RepoInterfaces;

import EntityClasses.Item;

import java.util.List;

public interface ItemInterface {
    void addItem(Item item) throws Exception;
    Item getItemById(int id) throws Exception;
    List<Item> getAllItems() throws Exception;
    void updateItem(Item item) throws Exception;
    void deleteItem(int id) throws Exception;
}
