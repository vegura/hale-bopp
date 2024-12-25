package me.vegura.resource.streaming;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import me.vegura.resource.storage.cloudstorage.CloudStorageVerticle;
import me.vegura.resource.vertx.HttpStreamingResourceController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class StreamingVerticle extends AbstractVerticle {

  // Public

  public static final String RESOURCE_PAUSE_ROUTE = "resource.pause";
  public static final String RESOURCE_STREAM_ROUTE = "resource.stream";
  public static final String RESOURCE_APPEND_ROUTE = "resource.append";

  // Private

  private static final Logger logger = LoggerFactory.getLogger(StreamingVerticle.class);

  private State currentMode = State.PAUSED;
  private final int CHUNK_SIZE_BYTES = 4096;

  private Buffer fullResourceBuffer = Buffer.buffer();
  private int positionInResourceBuffer;

  private final Queue<String> resourceQueue = new ArrayDeque<>();

  @Override
  public void start() {
    logger.info("Started Streaming Verticle");

    EventBus eventBus = vertx.eventBus();
    eventBus.consumer(RESOURCE_PAUSE_ROUTE, this::pause);
    eventBus.consumer(RESOURCE_STREAM_ROUTE, this::stream);
    eventBus.consumer(RESOURCE_APPEND_ROUTE, this::append);

    vertx.setPeriodic(100, this::streamResourceChunk);
  }

  private void append(Message<String> message) {
    if (resourceQueue.isEmpty() && currentMode == State.PAUSED)
      currentMode = State.STREAMING;

    resourceQueue.add(message.body());
  }

  private void pause(Message<?> message) {
    this.currentMode = State.PAUSED;
  }

  private void stream(Message<?> message) {
    this.currentMode = State.STREAMING;
  }

  private void streamResourceChunk(long id) {
    if (currentMode == State.PAUSED)
      return;

    if (fullResourceBuffer.length() == 0 && resourceQueue.isEmpty()) {
      currentMode = State.PAUSED;
      return;
    }

    if (fullResourceBuffer.length() == 0)
      followNextResource();

    if (positionInResourceBuffer < fullResourceBuffer.length() - 1) {
      processReadBuffer();
    } else
      flushBuffer();
  }

  private void processReadBuffer() {
    int startingPosition = positionInResourceBuffer;
    positionInResourceBuffer += CHUNK_SIZE_BYTES;

    if (positionInResourceBuffer > fullResourceBuffer.length() - 1) {
      positionInResourceBuffer = fullResourceBuffer.length() - 1;
    }

    logger.info(String.format("[%s \t- \t%s]", startingPosition, positionInResourceBuffer));

    vertx.eventBus().request(
      HttpStreamingResourceController.STREAMING_CONTENTS_ROUTE,
      fullResourceBuffer.getBytes(startingPosition, positionInResourceBuffer));
  }

  private void flushBuffer() {
    fullResourceBuffer = Buffer.buffer();
    positionInResourceBuffer = 0;
  }

  private void followNextResource() {
    Future<Message<byte[]>> resourceRequest = vertx.eventBus()
      .request(
        CloudStorageVerticle.ESB_RESOURCE_FETCH,
        new JsonObject().put("resource_id", resourceQueue.poll()));
    resourceRequest.onSuccess(asyncResult -> {
      logger.info("Fethced resource data -> " + asyncResult.body().length);
      fullResourceBuffer = Buffer.buffer(asyncResult.body());
      positionInResourceBuffer = 0;
    });
  }
}
