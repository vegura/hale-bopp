package me.vegura.resource.vertx;

import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import me.vegura.resource.storage.cloudstorage.CloudStorageVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static me.vegura.resource.streaming.StreamingVerticle.*;

public class HttpRESTResourceController {

  private static final Logger logger = LoggerFactory.getLogger(HttpRESTResourceController.class);

  private EventBus eventBus;

  public HttpRESTResourceController(EventBus eventBus) {
//    logger.info("HttpRESTResourceController");/**/
    this.eventBus = eventBus;
  }

  public void registerHandlingRESTRoutes(Router router) {
    router.post("/resources").handler(BodyHandler.create("/home/vegura/test/876jkb1").setMergeFormAttributes(true));
    router.post("/resources").handler(this::handlePostResource);
    router.get("/resources/:id").handler(this::handleGetResourceById);

    router.get("/stream/start").handler(this::handleStart);
    router.get("/stream/pause").handler(this::handlePause);
    router.get("/stream/append/:id").handler(this::handleAppend);
  }

  private void handleStart(RoutingContext ctx) {
    this.eventBus.publish(RESOURCE_STREAM_ROUTE, null);
  }

  private void handlePause(RoutingContext ctx) {
    this.eventBus.publish(RESOURCE_PAUSE_ROUTE, null);
  }

  private void handleAppend(RoutingContext ctx) {
    String resourceId = ctx.pathParam("id");
    this.eventBus.publish(RESOURCE_APPEND_ROUTE, resourceId);
  }

  private void handlePostResource(RoutingContext ctx) {
    byte[] resourceContents = ctx.body().buffer().getBytes();
    logger.info("Message on request -> {}", resourceContents.length);
    HttpServerResponse response = ctx.response();
    eventBus
      .request(CloudStorageVerticle.ESB_RESOURCE_SAVE, resourceContents, asyncResult -> {
        if (asyncResult.succeeded()){
          JsonObject resourceSaveResult = (JsonObject) asyncResult.<JsonObject>result().body();
          response.setStatusCode(201).end(resourceSaveResult.toString());
        }
      });
  }

  private void handleGetResourceById(RoutingContext ctx) {
    ctx.response().setStatusCode(200);
  }
}
