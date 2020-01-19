package dtu.messagingutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtu.exception.UserAlreadyExistsException;
import dtu.exception.UserNotFoundException;
import dtu.models.Customer;
import dtu.models.Event;
import dtu.models.EventType;
import dtu.models.Merchant;
import dtu.service.IUserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class Listener {
    private ObjectMapper objectMapper;
    private IUserService userService;
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public Listener(ObjectMapper objectMapper, IUserService userService, RabbitTemplate rabbitTemplate) {
        this.objectMapper = objectMapper;
        this.userService = userService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = {RabbitMQValues.USER_SERVICE_QUEUE_NAME})
    public void receiveEvent(Event event) {

        if(event.getType().equals(EventType.RETRIEVE_CUSTOMER)){
            String cpr = objectMapper.convertValue(event.getObject(), String.class);
            try {
                Customer customer = this.userService.getCustomer(cpr);
                Event responseSuccess = new Event(EventType.RETRIEVE_CUSTOMER_RESPONSE, customer);
                this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.DTU_SERVICE_ROUTING_KEY, responseSuccess);
            } catch (UserNotFoundException e) {
                Event responseFailure = new Event(EventType.RETRIEVE_CUSTOMER_RESPONSE, null);
                this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.DTU_SERVICE_ROUTING_KEY, responseFailure);
            }
        }
        if(event.getType().equals(EventType.RETRIEVE_MERCHANT)){
            String cpr = objectMapper.convertValue(event.getObject(), String.class);
            try {
                Merchant merchant = this.userService.getMerchant(cpr);
                Event responseSuccess = new Event(EventType.RETRIEVE_MERCHANT_RESPONSE, merchant);
                this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.DTU_SERVICE_ROUTING_KEY, responseSuccess);
            } catch (UserNotFoundException e) {
                Event responseFailure = new Event(EventType.RETRIEVE_MERCHANT_RESPONSE, null);
                this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.DTU_SERVICE_ROUTING_KEY, responseFailure);
            }
        }
        if(event.getType().equals(EventType.CREATE_CUSTOMER)){
            Customer customer = objectMapper.convertValue(event.getObject(), Customer.class);
            try {
                this.userService.registerCustomer(customer);
                Event responseSuccess = new Event(EventType.CREATE_CUSTOMER_RESPONSE, "Created");
                System.out.println(userService.getCustomer(customer.getCprNumber()).getFirstName());
                this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.DTU_SERVICE_ROUTING_KEY, responseSuccess);
            } catch (Exception e) {
                Event responseFailure = new Event(EventType.CREATE_CUSTOMER_RESPONSE, "Failure");
                this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.DTU_SERVICE_ROUTING_KEY, responseFailure);
            }
        }
        if(event.getType().equals(EventType.CREATE_MERCHANT)){
            Merchant merchant = objectMapper.convertValue(event.getObject(), Merchant.class);
            try {
                this.userService.registerMerchant(merchant);
                Event responseSuccess = new Event(EventType.CREATE_MERCHANT_RESPONSE, "Created");
                this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.DTU_SERVICE_ROUTING_KEY, responseSuccess);
            } catch (Exception e) {
                Event responseFailure = new Event(EventType.CREATE_MERCHANT_RESPONSE, "Failed to create");
                this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.DTU_SERVICE_ROUTING_KEY, responseFailure);
            }
        }
        if(event.getType().equals(EventType.DELETE_MERCHANT)){
            String cpr = objectMapper.convertValue(event.getObject(), String.class);
            try {
                if(this.userService.deleteMerchant(cpr)){
                    Event responseSuccess = new Event(EventType.DELETE_MERCHANT_RESPONSE, "Deleted");
                    this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.DTU_SERVICE_ROUTING_KEY, responseSuccess);
                }
            } catch (Exception e) {
                Event responseFailure = new Event(EventType.DELETE_MERCHANT_RESPONSE, "Failed to delete");
                this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.DTU_SERVICE_ROUTING_KEY, responseFailure);
            }
        }
        if(event.getType().equals(EventType.DELETE_CUSTOMER)){
            String cpr = objectMapper.convertValue(event.getObject(), String.class);
            try {
                if(this.userService.deleteCustomer(cpr)){
                    Event responseSuccess = new Event(EventType.DELETE_CUSTOMER_RESPONSE, "Deleted");
                    this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.DTU_SERVICE_ROUTING_KEY, responseSuccess);
                }
            } catch (Exception e) {
                Event responseFailure = new Event(EventType.DELETE_CUSTOMER_RESPONSE, "Failed to delete");
                this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.DTU_SERVICE_ROUTING_KEY, responseFailure);
            }
        }

    }

}
