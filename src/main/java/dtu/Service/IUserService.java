package dtu.Service;

import dtu.Exception.*;
import dtu.Model.*;

public interface IUserService {

    boolean customerExists(Customer customer);

    boolean merchantExists(Merchant merchant);

    String registerCustomer(Customer customer) throws UserAlreadyExistsException;

    String registerMerchant(Merchant merchant) throws UserAlreadyExistsException;

    boolean deleteCustomer(Customer customer);

    boolean deleteMerchant(Merchant merchant);
}
