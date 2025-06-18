package RepoLayer.RepoInterfaces;

import EntityClasses.Customer;

import java.util.List;

public interface CustomerInterface {
    void addCustomer(Customer user) throws Exception;
    Customer getCustomerById(int id) throws Exception;
    List<Customer> getAllCustomers() throws Exception;
    void updateCustomer(Customer customer) throws Exception;
    void deleteCustomer(int id) throws Exception;
}
