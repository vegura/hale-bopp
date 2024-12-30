package me.vegura.resource.mq.consumer.rabbit;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.rabbitmq.RabbitMQClient;
import io.vertx.rabbitmq.RabbitMQConsumer;
import io.vertx.rabbitmq.RabbitMQMessage;
import me.vegura.resource.storage.cloudstorage.CloudStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static me.vegura.resource.storage.cloudstorage.CloudStorageVerticle.ESB_RESOURCE_SAVE;

public class RabbitMqConsumerVerticle extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(CloudStorageService.class);

  private static final String QUEUE_RECEIVE = "receive.resource";

  private RabbitMQClient client;

  private void init() {
    client = RabbitMQClient.create(vertx, RabbitMqConfig.defaultOptions());
    client.start(asyncResult -> {
      if (asyncResult.succeeded()) {
        logger.info("Connection to RabbitMQ is successfully established");
      } else {
        logger.error("Error while establishing connection to RabbitMQ");
      }
    });
  }

  @Override
  public void start() {
    init();

    client.basicConsumer(QUEUE_RECEIVE, this::consumingResourceSetupHandler);
  }

  public void consumingResourceSetupHandler(AsyncResult<RabbitMQConsumer> consumerAsyncResult) {
    if (consumerAsyncResult.succeeded()) {
      RabbitMQConsumer consumer = consumerAsyncResult.result();
      consumer.handler(this::consumingResourceHandler);
    }
  }

  public void consumingResourceHandler(RabbitMQMessage message) {
    vertx.eventBus()
      .request(ESB_RESOURCE_SAVE, message.body(), replyResult -> {
        logger.info("result -> " + replyResult.result().body());
      });
  }
}
