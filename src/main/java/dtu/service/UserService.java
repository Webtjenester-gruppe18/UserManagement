package dtu.service;

import dtu.controller.ControlReg;
import dtu.database.*;
import dtu.exception.*;
import dtu.models.Customer;
import dtu.models.DTUPayUser;
import dtu.models.Merchant;


public class UserService implements IUserService {

    IUserDatabase database = ControlReg.getUserDatabase();

    @Override
    public boolean customerExists(Customer customer) {

        for (Customer currentCustomer : database.getAllCustomers()) {
            if (currentCustomer.equals(customer)) {
                return true;
            }
        }

        return false;
    }


    @Override
    public boolean merchantExists(Merchant merchant) {
        for (Merchant currentMerchant : database.getAllMerchants()) {
            if (currentMerchant.equals(merchant)) {
                return true;
            }
        }

        return false;
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

        if (customerExists(customer)) {
            throw new UserAlreadyExistsException("This user already exists in the database.");
        }

        return this.database.saveCustomer(customer);
    }

    @Override
    public String registerMerchant(Merchant merchant) throws UserAlreadyExistsException {
        if (merchantExists(merchant)) {
            throw new UserAlreadyExistsException("This user already exists in the database.");
        }

        return this.database.saveMerchant(merchant);
    }

    @Override
    public boolean deleteCustomer(Customer customer) {

        return this.database.deleteCustomer(customer);
    }

    @Override
    public boolean deleteMerchant(Merchant merchant) {
        return this.database.deleteMerchant(merchant);
    }

    @Override
    public void addTransactionToUserByAccountId(String accountId, String transactionId) {
        DTUPayUser user = this.database.getDTUPayUserByAccountId(accountId);
        user.getTransactionIds().add(transactionId);
    }
}
