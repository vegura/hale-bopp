package me.vegura.resource.vertx;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import me.vegura.resource.streaming.StreamingVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class HttpStreamingResourceController {

  public static final String STREAMING_CONTENTS_ROUTE = "api.streaming.push";

  private static final Logger logger = LoggerFactory.getLogger(StreamingVerticle.class);

  private final Set<HttpServerResponse> streamers = new HashSet<>();

  private EventBus eventBus;

  public HttpStreamingResourceController(EventBus eventBus) {
    this.eventBus = eventBus;

    this.eventBus.consumer(STREAMING_CONTENTS_ROUTE, (Message<byte[]> message) -> {
      writeData(message.body());
    });
  }

  public void httpHandler(HttpServerRequest request) {
    if ("/".equals(request.path())) {
      openAudioStream(request);
      return;
    }

    request.response().setStatusCode(404).end();
  }

  // TODO: refactor to general format
  private void openAudioStream(HttpServerRequest request) {
    HttpServerResponse response = request.response()
      .putHeader("Content-Type", "audio/mpeg")
      .setStatusCode(200)
      .setChunked(true);
    streamers.add(response);
    response.endHandler(v -> {
      streamers.remove(response);
      logger.info("A streamer left");
    });
  }

  public void writeData(byte[] data) {
    for (HttpServerResponse streamer : streamers) {
      if (!streamer.writeQueueFull()) {
        streamer.write(Buffer.buffer(data));
      }
    }
  }
}
