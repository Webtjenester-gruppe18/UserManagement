package dtu.database;

import dtu.exception.*;
import dtu.models.*;

import java.util.ArrayList;
import java.util.HashMap;

public interface IUserDatabase {

    Customer getCustomer(String cprNumber) throws UserNotFoundException;

    Merchant getMerchant(String cprNumber) throws UserNotFoundException;

    HashMap<String,Customer> getAllCustomers();

    HashMap<String,Merchant> getAllMerchants();

    String saveCustomer(Customer customer);

    String saveMerchant(Merchant merchant);

    boolean deleteCustomer(String cprNumber);

    boolean deleteMerchant(String cprNumber);
}
