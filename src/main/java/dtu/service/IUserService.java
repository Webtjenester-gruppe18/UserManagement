package dtu.service;

import dtu.exception.UserAlreadyExistsException;
import dtu.exception.UserNotFoundException;
import dtu.models.Customer;
import dtu.models.Merchant;
import org.springframework.stereotype.Component;


@Component
public interface IUserService {

    boolean customerExists(String cprNumber);

    boolean merchantExists(String cprNumber);

    Customer getCustomer(String cprNumber) throws UserNotFoundException;

    Merchant getMerchant(String cprNumber) throws UserNotFoundException;

    String registerCustomer(Customer customer) throws UserAlreadyExistsException;

    String registerMerchant(Merchant merchant) throws UserAlreadyExistsException;

    boolean deleteCustomer(String cprNumber);

    boolean deleteMerchant(String cprNumber);

}
