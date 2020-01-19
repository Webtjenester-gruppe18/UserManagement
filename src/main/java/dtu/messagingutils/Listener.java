package dtu.messagingutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtu.models.Event;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
public class Listener {
    private ObjectMapper objectMapper;

    public Listener(ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = {RabbitMQValues.USER_SERVICE_QUEUE_NAME})
    public void receiveEvent(Event event) {

        System.out.println(event.getType());
    }

}
