package CacheLayer;

import EntityClasses.Customer;
import EntityClasses.Item;
import RepoLayer.CustomerRepo;
import RepoLayer.ItemRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cache {
    private static final Map<Integer,Customer> CUSTOMER_CACHE = new HashMap<>();
    private static final Map<Integer,Item> ITEM_CACHE = new HashMap<>();

    private static final CustomerRepo CUSTOMER_REPO = new CustomerRepo();
    private static final ItemRepo ITEM_REPO = new ItemRepo();

    public static void preloadAll() throws Exception {
        preloadItems();
        preloadCustomers();
    }

    public static void preloadCustomers() throws Exception {
        CUSTOMER_CACHE.clear();

        List<Customer> customers = CUSTOMER_REPO.getAllCustomers();
        for (Customer customer : customers) {
            CUSTOMER_CACHE.put(customer.getId(), customer);
        }
    }

    public static void preloadItems() throws Exception {
        ITEM_CACHE.clear();

        List<Item> items = ITEM_REPO.getAllItems();
        for (Item item : items) {
            ITEM_CACHE.put(item.getItemID(), item);
        }
    }

    public static Customer getCustomer(int id) throws Exception {
        return CUSTOMER_CACHE.get(id);
    }

    public static Item getItem(int id) throws Exception {
        return ITEM_CACHE.get(id);
    }

    public static void addOrUpdateCustomer(Customer customer) throws Exception {
        CUSTOMER_CACHE.put(customer.getId(), customer);
    }

    public static void addOrUpdateItem(Item item) throws Exception {
        ITEM_CACHE.put(item.getItemID(), item);
    }

    public static void deleteCustomer(int id) throws Exception {
        CUSTOMER_CACHE.remove(id);
    }

    public static void deleteItem(int id) throws Exception {
        ITEM_CACHE.remove(id);
    }
}
