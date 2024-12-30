package me.vegura.resource.mq.consumer.rabbit;

import io.vertx.rabbitmq.RabbitMQOptions;
import io.vertx.reactivex.rabbitmq.RabbitMQClient;

/*
rabbitmq.resource-upload.notification.routingkey=rabbitmq.resource-data
rabbitmq.resource-upload.notification.queue=resource.upload.notification
rabbitmq.resource-upload.notification.exchange=rabbitmq.exchange

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
 */

public class RabbitMqConfig {
  public static RabbitMQOptions defaultOptions() {
    RabbitMQOptions rabbitMqConfiguration = new RabbitMQOptions();
    rabbitMqConfiguration.setHost("localhost");
    rabbitMqConfiguration.setPort(5672);
    rabbitMqConfiguration.setUser("guest");
    rabbitMqConfiguration.setPassword("guest");

    return rabbitMqConfiguration;
  }
}
