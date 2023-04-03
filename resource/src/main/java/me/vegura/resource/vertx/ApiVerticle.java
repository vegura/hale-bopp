package me.vegura.resource.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static me.vegura.resource.storage.S3StorageVerticle.ESB_RESOURCE_SAVE;

public class ApiVerticle extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(ApiVerticle.class);

  @Override
  public void start() {
    Router router = Router.router(vertx);
    router.post("/resources").handler(BodyHandler.create("~/test/876jkb").setMergeFormAttributes(true));

    router.post("/resources")
      .handler(this::handlePostResource);
    router.get("/resources/:id").handler(this::handleGetResourceById);
    router.delete("/resource/:ids").handler(this::handleDeleteResources);

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8888);
  }

  private void handlePostResource(RoutingContext ctx) {
    byte[] resourceContents = ctx.body().buffer().getBytes();
    HttpServerResponse response = ctx.response();
    logger.info("Message on request -> {}", resourceContents.length);
    vertx
      .eventBus()
      .request(ESB_RESOURCE_SAVE, resourceContents, asyncResult -> {
        if (asyncResult.succeeded()){
          JsonObject resourceSaveResult = (JsonObject) asyncResult.<JsonObject>result().body();
          response.setStatusCode(201).end(resourceSaveResult.toString());
        }
      });
  }

  private void handleGetResourceById(RoutingContext ctx) {
    
  }

  private void handleDeleteResources(RoutingContext ctx) {

  }
}
