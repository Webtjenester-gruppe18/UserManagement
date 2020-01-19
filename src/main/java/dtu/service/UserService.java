package dtu.service;

import dtu.database.IUserDatabase;
import dtu.exception.UserAlreadyExistsException;
import dtu.exception.UserNotFoundException;
import dtu.models.Customer;
import dtu.models.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private IUserDatabase database;

    @Autowired
    public UserService(IUserDatabase database) {

        this.database = database;
    }

    @Override
    public boolean customerExists(String cprNumber) {
        return database.getAllCustomers().containsKey(cprNumber);
    }


    @Override
    public boolean merchantExists(String cprNumber) {
        return database.getAllMerchants().containsKey(cprNumber);
    }

    @Override
    public Customer getCustomer(String cprNumber) throws UserNotFoundException {

        return database.getCustomer(cprNumber);
    }

    @Override
    public Merchant getMerchant(String cprNumber) throws UserNotFoundException {
        return database.getMerchant(cprNumber);
    }

    @Override
    public String registerCustomer(Customer customer) throws UserAlreadyExistsException {

        if (customerExists(customer.getCprNumber())) {
            throw new UserAlreadyExistsException("This user already exists in the database.");
        }

        return this.database.saveCustomer(customer);
    }

    @Override
    public String registerMerchant(Merchant merchant) throws UserAlreadyExistsException {
        if (merchantExists(merchant.getCprNumber())) {
            throw new UserAlreadyExistsException("This user already exists in the database.");
        }

        return this.database.saveMerchant(merchant);
    }

    @Override
    public boolean deleteCustomer(String cprNumber) {

        return this.database.deleteCustomer(cprNumber);
    }

    @Override
    public boolean deleteMerchant(String cprNumber) {
        return this.database.deleteMerchant(cprNumber);
    }

}
