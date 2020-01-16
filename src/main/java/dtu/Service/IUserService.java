package dtu.Service;

import dtu.Exception.*;
import dtu.Model.*;

public interface IUserService {

    boolean customerExists(Customer customer);

    boolean merchantExists(Merchant merchant);

    Customer getCustomer(String cprNumber) throws UserNotFoundException;

    Merchant getMerchant (String cprNumber) throws UserNotFoundException;

    String registerCustomer(Customer customer) throws UserAlreadyExistsException;

    String registerMerchant(Merchant merchant) throws UserAlreadyExistsException;

    boolean deleteCustomer(Customer customer);

    boolean deleteMerchant(Merchant merchant);

    void addTransactionToUserByAccountId(String accountId, String transactionId);
}
