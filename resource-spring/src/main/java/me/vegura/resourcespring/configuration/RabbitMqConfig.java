package me.vegura.resourcespring.configuration;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String AMQP_RESOURCE_NOTIFICATION_QUEUE = "resource.upload.notification";

//    @Bean
//    public Queue resourceUploadNotification() {
//        return new Queue(AMQP_RESOURCE_NOTIFICATION_QUEUE, true);
//    }

    @Bean
    public Queue queue() {
        return new Queue(AMQP_RESOURCE_NOTIFICATION_QUEUE, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("rabbitmq.exchange");
    }

//    @Bean
//    public Binding binding(Queue queue, DirectExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with("rabbitmq.resource-data");
//    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setVirtualHost("/");
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

}
