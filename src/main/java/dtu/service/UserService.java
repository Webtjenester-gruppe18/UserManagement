package dtu.service;

/**
 * @author Mathias Hansen s175112
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import dtu.database.IUserDatabase;
import dtu.exception.UserAlreadyExistsException;
import dtu.exception.UserNotFoundException;
import dtu.messagingutils.IEventReceiver;
import dtu.messagingutils.IEventSender;
import dtu.messagingutils.RabbitMQValues;
import dtu.models.Customer;
import dtu.models.Event;
import dtu.models.EventType;
import dtu.models.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService, IEventReceiver {

    private IUserDatabase database;
    private IEventSender eventSender;
    private ObjectMapper objectMapper;

    @Autowired
    public UserService(IUserDatabase database, IEventSender eventSender) {
        this.objectMapper = new ObjectMapper();
        this.database = database;
        this.eventSender = eventSender;
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

    @Override
    public void receiveEvent(Event event) throws Exception {
        String cpr;
        switch (event.getType()) {
            case RETRIEVE_CUSTOMER:
                cpr = objectMapper.convertValue(event.getObject(), String.class);
                try {
                    Customer customer = this.getCustomer(cpr);
                    Event responseSuccess = new Event(EventType.RETRIEVE_CUSTOMER_RESPONSE_SUCCESS, customer, RabbitMQValues.DTU_SERVICE_ROUTING_KEY);
                    this.eventSender.sendEvent(responseSuccess);
                } catch (UserNotFoundException e) {
                    Event responseFailure = new Event(EventType.RETRIEVE_CUSTOMER_RESPONSE_FAILED, e.getMessage(), RabbitMQValues.DTU_SERVICE_ROUTING_KEY);
                    this.eventSender.sendEvent(responseFailure);
                }
                break;
            case RETRIEVE_MERCHANT:
                cpr = objectMapper.convertValue(event.getObject(), String.class);
                try {
                    Merchant merchant = this.getMerchant(cpr);
                    Event responseSuccess = new Event(EventType.RETRIEVE_MERCHANT_RESPONSE, merchant, RabbitMQValues.DTU_SERVICE_ROUTING_KEY);
                    this.eventSender.sendEvent(responseSuccess);
                } catch (UserNotFoundException e) {
                    Event responseFailure = new Event(EventType.RETRIEVE_MERCHANT_RESPONSE, e.getMessage(), RabbitMQValues.DTU_SERVICE_ROUTING_KEY);
                    this.eventSender.sendEvent(responseFailure);
                }
                break;
            case CREATE_CUSTOMER:
                Customer customer = objectMapper.convertValue(event.getObject(), Customer.class);
                try {
                    this.registerCustomer(customer);
                    Event responseSuccess = new Event(EventType.CREATE_CUSTOMER_RESPONSE, "Created", RabbitMQValues.DTU_SERVICE_ROUTING_KEY);
                    this.eventSender.sendEvent(responseSuccess);
                } catch (Exception e) {
                    Event responseFailure = new Event(EventType.CREATE_CUSTOMER_RESPONSE, e.getMessage(), RabbitMQValues.DTU_SERVICE_ROUTING_KEY);
                    this.eventSender.sendEvent(responseFailure);
                }
                break;
            case CREATE_MERCHANT:
                Merchant merchant = objectMapper.convertValue(event.getObject(), Merchant.class);
                try {
                    this.registerMerchant(merchant);
                    Event responseSuccess = new Event(EventType.CREATE_MERCHANT_RESPONSE, "Created", RabbitMQValues.DTU_SERVICE_ROUTING_KEY);
                    this.eventSender.sendEvent(responseSuccess);
                } catch (Exception e) {
                    Event responseFailure = new Event(EventType.CREATE_MERCHANT_RESPONSE, e.getMessage(), RabbitMQValues.DTU_SERVICE_ROUTING_KEY);
                    this.eventSender.sendEvent(responseFailure);
                }
                break;
            case DELETE_MERCHANT:
                cpr = objectMapper.convertValue(event.getObject(), String.class);

                Event deleteMerchantResponse = new Event();
                deleteMerchantResponse.setType(EventType.DELETE_MERCHANT_RESPONSE);
                deleteMerchantResponse.setRoutingKey(RabbitMQValues.DTU_SERVICE_ROUTING_KEY);

                if (this.deleteCustomer(cpr)) {
                    deleteMerchantResponse.setObject(cpr + " is deleted");
                } else {
                    deleteMerchantResponse.setObject("Failed to delete");
                }

                this.eventSender.sendEvent(deleteMerchantResponse);
                break;

            case DELETE_CUSTOMER:
                cpr = objectMapper.convertValue(event.getObject(), String.class);

                Event deleteCustomerResponse = new Event();
                deleteCustomerResponse.setType(EventType.DELETE_CUSTOMER_RESPONSE);
                deleteCustomerResponse.setRoutingKey(RabbitMQValues.DTU_SERVICE_ROUTING_KEY);

                if (this.deleteCustomer(cpr)) {
                    deleteCustomerResponse.setObject(cpr + " is deleted");
                } else {
                    deleteCustomerResponse.setObject("Failed to delete");
                }

                this.eventSender.sendEvent(deleteCustomerResponse);
                break;
        }
    }
}
