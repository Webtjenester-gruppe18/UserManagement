package dtu.ws18.usermanagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @RequestMapping("/usermanager")
    public String index() {
        return "ToDo: Implement Usermanager";
    }
}
