package me.vegura.resourceprocessor.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String AMQP_RESOURCE_NOTIFICATION_QUEUE = "resource.upload.notification";

    @Bean
    public Queue createResourceCreateNotification() {
        return new Queue(AMQP_RESOURCE_NOTIFICATION_QUEUE);
    }
}
