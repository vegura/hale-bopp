package me.vegura.resource.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiVerticle extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(ApiVerticle.class);

  private void initRESTController() {
    Router router = Router.router(vertx);
    HttpRESTResourceController httpRESTResourceController = new HttpRESTResourceController(vertx.eventBus());
    httpRESTResourceController.registerHandlingRESTRoutes(router);
    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8088, http -> {
        if (http.succeeded()) {
          System.out.println("HTTP server started on port 8088");
        } else {
          http.cause().printStackTrace();
        }
      });
  }

  private void initStreamingController() {
    HttpStreamingResourceController httpStreamingResourceController = new HttpStreamingResourceController(vertx.eventBus());
    vertx.createHttpServer()
      .requestHandler(httpStreamingResourceController::httpHandler)
      .listen(8001, http -> {
        if (http.succeeded()) {
          System.out.println("Streaming server started on port 8001");
        } else {
          http.cause().printStackTrace();
        }
      });
  }

  @Override
  public void start() {
    logger.info("Starting API verticle");
    initRESTController();
    initStreamingController();
  }
}
