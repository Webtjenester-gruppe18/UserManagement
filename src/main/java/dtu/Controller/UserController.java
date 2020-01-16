package dtu.Controller;
import dtu.Exception.UserAlreadyExistsException;
import dtu.Exception.UserNotFoundException;
import dtu.Model.Customer;
import dtu.Model.Merchant;
import dtu.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
public class UserController {

    private UserService userService = new UserService();

    @RequestMapping (path = "/usermanager/customer", method = RequestMethod.POST)
    public ResponseEntity<Object> postCustomer(@RequestBody Customer customer)  {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.registerCustomer(customer));
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping (path = "/usermanager/merchant", method = RequestMethod.POST)
    public ResponseEntity<Object> postMerchant(@RequestBody Merchant merchant)  {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.registerMerchant(merchant));
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping (path = "/usermanager/customer/{cprNumber}", method = RequestMethod.GET)
    public ResponseEntity<Object> getCustomer (@PathVariable @NotNull String cprNumber) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getCustomer(cprNumber));
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping (path = "/usermanager/merchant/{cprNumber}", method = RequestMethod.GET)
    public ResponseEntity<Object> getMerchant (@PathVariable @NotNull String cprNumber)  {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getMerchant(cprNumber));
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping (path = "/usermanager/customer/{cprNumber}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteCustomer (@RequestBody Customer customer) {

        userService.deleteCustomer(customer);

        return new ResponseEntity<>("The customer is deleted", HttpStatus.OK);
    }

    @RequestMapping (path = "/usermanager/merchant/{cprNumber}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteMerchant (@RequestBody Merchant merchant) {

        userService.deleteMerchant(merchant);

        return new ResponseEntity<>("The merchant is deleted", HttpStatus.OK);
    }

}
