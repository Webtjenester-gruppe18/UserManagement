package dtu.messagingutils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import dtu.models.Event;
import gherkin.deps.com.google.gson.Gson;
import org.springframework.stereotype.Component;

/**
 * 	Provided code from the instructor
 */
@Component
public class RabbitMqSender implements IEventSender {

    @Override
    public void sendEvent(Event event) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitMQValues.HOST_NAME);

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(RabbitMQValues.TOPIC_EXCHANGE_NAME, "topic");
            String eventToSent = new Gson().toJson(event);
            System.out.println("[x] sending " + eventToSent);

            channel.basicPublish(RabbitMQValues.TOPIC_EXCHANGE_NAME, event.getRoutingKey(), null, eventToSent.getBytes("UTF-8"));
        }
    }

}