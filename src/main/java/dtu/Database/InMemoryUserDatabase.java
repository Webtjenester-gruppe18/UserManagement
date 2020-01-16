
package dtu.Database;

import dtu.Exception.*;
import dtu.Model.*;
import java.util.ArrayList;

public class InMemoryUserDatabase implements IUserDatabase {

    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Merchant> merchants = new ArrayList<>();


    @Override
    public Customer getCustomer(String cprNumber) throws UserNotFoundException {

        for (Customer currentCustomer : this.customers) {
            if (currentCustomer.getCprNumber().equals(cprNumber)) {
                return currentCustomer;
            }
        }
        throw new UserNotFoundException("Could not find a user on that cprNumber " + cprNumber);
    }

    @Override
    public Merchant getMerchant(String cprNumber) throws UserNotFoundException {

        for (Merchant currentMerchant : this.merchants) {
            if (currentMerchant.getCprNumber().equals(cprNumber)){
                return currentMerchant;
            }
        }
        throw new UserNotFoundException("Could not find a user on that cprNumber " + cprNumber);
    }

    @Override
    public ArrayList<Customer> getAllCustomers() {
        return this.customers;
    }

    @Override
    public ArrayList<Merchant> getAllMerchants() {
        return this.merchants;
    }

    @Override
    public String saveCustomer(Customer customer) {

        this.customers.add(customer);

        return customer.getCprNumber();
    }

    @Override
    public String saveMerchant(Merchant merchant) {

        this.merchants.add(merchant);

        return merchant.getCprNumber();
    }

    @Override
    public boolean deleteCustomer(Customer customer) {

        this.customers.remove(customer);

        return true;
    }

    @Override
    public boolean deleteMerchant(Merchant merchant) {

        this.merchants.remove(merchant);

        return true;
    }

    public DTUPayUser getDTUPayUserByAccountId(String accountId) {

        for (DTUPayUser customer : this.customers) {
            if (customer.getAccountId().equals(accountId)) {
                return customer;
            }
        }

        for (DTUPayUser merchant : this.merchants) {
            if (merchant.getAccountId().equals(accountId)) {
                return merchant;
            }
        }

        return null;
    }
}
