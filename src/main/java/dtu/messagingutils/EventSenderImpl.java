package dtu.messagingutils;

import dtu.models.Event;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author Emil Glimø Vinkel - s175107
 */

@Component
public class EventSenderImpl implements IEventSender {
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public EventSenderImpl() {
        this.rabbitTemplate = getRabbitTemplate();
    }

    private RabbitTemplate getRabbitTemplate() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory("rabbitmq");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Override
    public void sendEvent(Event event) throws Exception {
        this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, event.getRoutingKey(), event);
    }
}
