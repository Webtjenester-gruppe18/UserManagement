package dtu.usermanagement;

import dtu.database.IUserDatabase;
import dtu.database.InMemoryUserDatabase;
import dtu.exception.UserNotFoundException;
import dtu.messagingutils.IEventSender;
import dtu.models.Customer;
import dtu.models.Event;
import dtu.models.EventType;
import dtu.models.Merchant;
import dtu.service.UserService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration
@SpringBootTest
public class UserManagementSteps {

    private Customer customer;
    private Merchant merchant;
    private IEventSender eventSender = mock(IEventSender.class);
    private IUserDatabase database = new InMemoryUserDatabase();
    private UserService userService = new UserService(database, eventSender);



    @When("the service receives the {string} event")
    public void the_service_receives_the_event(String string) throws Exception {
        Event event = new Event();
        event.setType(EventType.valueOf(string));
        if (event.getType().equals(EventType.CREATE_CUSTOMER)) {
            customer = new Customer();
            customer.setFirstName("Bo");
            customer.setLastName("Nielsen");
            customer.setCprNumber("12345678");
            customer.setAccountId("123");
            event.setObject(customer);
        } else if (event.getType().equals(EventType.CREATE_MERCHANT)) {
            merchant = new Merchant();
            merchant.setFirstName("Ib");
            merchant.setLastName("Jensen");
            merchant.setCprNumber("87654321");
            merchant.setAccountId("456");
            event.setObject(merchant);
        } else if (event.getType().equals(EventType.DELETE_CUSTOMER)) {
            event.setObject("12345678");
        } else if (event.getType().equals(EventType.DELETE_MERCHANT)) {
            event.setObject("87654321");
        } else if (event.getType().equals(EventType.RETRIEVE_CUSTOMER)) {
            customer = new Customer();
            customer.setFirstName("Ib");
            customer.setLastName("Nielsen");
            customer.setCprNumber("999999999");
            customer.setAccountId("123");

            userService.registerCustomer(customer);
            event.setObject(customer.getCprNumber());
        } else if (event.getType().equals(EventType.RETRIEVE_MERCHANT)){
            merchant = new Merchant();
            merchant.setFirstName("Ib");
            merchant.setLastName("Jensen");
            merchant.setCprNumber("888888888");
            merchant.setAccountId("456");

            userService.registerMerchant(merchant);
            event.setObject(merchant.getCprNumber());
        }

        userService.receiveEvent(event);
    }

    @Then("the customer is registered")
    public void the_customer_is_registered() throws UserNotFoundException {
        Customer cs = userService.getCustomer(customer.getCprNumber());

        assertEquals(cs.getCprNumber(), customer.getCprNumber());
    }

    @Then("the {string} event is broadcast")
    public void the_event_is_broadcast(String string) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        ArgumentCaptor<Event> argumentCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventSender, atLeastOnce()).sendEvent(argumentCaptor.capture());
        assertEquals(EventType.valueOf(string), argumentCaptor.getValue().getType());
    }

    @Then("the merchant is registered")
    public void the_merchant_is_registered() throws UserNotFoundException {
        Merchant mr = userService.getMerchant(merchant.getCprNumber());

        assertEquals(mr.getCprNumber(), merchant.getCprNumber());
    }

    @Then("the customer is deleted")
    public void the_customer_is_deleted() throws UserNotFoundException {

        assertFalse(userService.customerExists("12345678"));
    }

    @Then("the merchant is deleted")
    public void the_merchant_is_deleted() {
        assertFalse(userService.merchantExists("87654321"));
    }

    @Then("the customer is received")
    public void the_customer_is_received() {
        assertTrue(userService.customerExists("999999999"));
    }

    @Then("the merchant is received")
    public void the_merchant_is_received() {
        assertTrue(userService.merchantExists("888888888"));
    }
}
