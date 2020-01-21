package dtu;

import dtu.database.IUserDatabase;
import dtu.database.InMemoryUserDatabase;
import dtu.messagingutils.EventReceiverImpl;
import dtu.messagingutils.EventSenderImpl;
import dtu.messagingutils.IEventSender;
import dtu.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class UsermanagementApplication {

    public static void main(String[] args) throws Exception {

        SpringApplication.run(UsermanagementApplication.class, args);

        new UsermanagementApplication().startUp();

    }

    private void startUp() throws Exception {
        IUserDatabase userDatabase = new InMemoryUserDatabase();
        IEventSender eventSender = new EventSenderImpl();
        UserService userService = new UserService(userDatabase, eventSender);
        new EventReceiverImpl(userService).listen();
    }

}
