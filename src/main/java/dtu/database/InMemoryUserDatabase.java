
package dtu.database;

import dtu.exception.UserNotFoundException;
import dtu.models.Customer;
import dtu.models.Merchant;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author Mathias Hansen s175112
 */

@Component
public class InMemoryUserDatabase implements IUserDatabase {

    private HashMap<String, Customer> customers = new HashMap<>();
    private HashMap<String, Merchant> merchants = new HashMap<>();


    @Override
    public Customer getCustomer(String cprNumber) throws UserNotFoundException {
        Customer customer = customers.get(cprNumber);
        if (customer != null) {
            return customer;
        }
        throw new UserNotFoundException("Could not find a customer with that cprnumber" + cprNumber);
    }

    @Override
    public Merchant getMerchant(String cprNumber) throws UserNotFoundException {
        Merchant merchant = merchants.get(cprNumber);
        if (merchant != null) {
            return merchant;
        }
        throw new UserNotFoundException("Could not find a merchant with that cprnumber" + cprNumber);
    }

    @Override
    public HashMap<String, Customer> getAllCustomers() {
        return this.customers;
    }

    @Override
    public HashMap<String, Merchant> getAllMerchants() {
        return this.merchants;
    }

    @Override
    public String saveCustomer(Customer customer) {
        this.customers.put(customer.getCprNumber(), customer);
        return customer.getCprNumber();
    }

    @Override
    public String saveMerchant(Merchant merchant) {
        this.merchants.put(merchant.getCprNumber(), merchant);
        return merchant.getCprNumber();
    }

    @Override
    public boolean deleteCustomer(String cprNumber) {
        return this.customers.remove(cprNumber) != null;
    }

    @Override
    public boolean deleteMerchant(String cprNumber) {
        return this.merchants.remove(cprNumber) != null;
    }

}
