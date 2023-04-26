package me.vegura.resourceprocessor.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = "resource.upload.notification")
@Slf4j
public class ResourceConsumer {

    @RabbitHandler
    public void handleResourceCreateNotification(final String message) {
        log.info("Received message: {}", message);
    }
}
