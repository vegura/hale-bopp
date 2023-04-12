package me.vegura.resource.database;

import io.vertx.config.ConfigRetriever;
import io.vertx.core.AsyncResult;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PostgresDatabaseConfig {

  private static final Logger log = LoggerFactory.getLogger(PostgresDatabaseConfig.class);

  // Routes

  private static final String CONFIG_PG_HOST = "postgresql.host";
  private static final String CONFIG_PG_PORT = "postgresql.port";
  private static final String CONFIG_PG_DATABASE = "postgresql.database";
  private static final String CONFIG_PG_USERNAME = "postgresql.username";
  private static final String CONFIG_PG_PASSWORD = "postgresql.password";
  private static final String CONFIG_PG_POOL_MAX_SIZE = "postgresql.pool.maxsize";

  private static final String CONFIG_DB_EB_QUEUE = "resource.db.eb.address";

  // Variables

  private String host;
  private String port;
  private String database;
  private String username;
  private String password;
  private int poolMaxSize;

  private Vertx vertx;


  public PostgresDatabaseConfig(Vertx vertx) {
    this.vertx = vertx;
    initializeDatabaseConfiguration();
  }

  public void initializeDatabaseConfiguration() {
    ConfigRetriever configRetriever = ConfigRetriever.create(vertx);
    configRetriever.getConfig(this::handleRetrievingConfigurations);
  }

  private void handleRetrievingConfigurations(AsyncResult<JsonObject> result) {
    if (result.failed()) {
      throw new RuntimeException(result.cause());
    }

    JsonObject configs = result.result();
    this.host = configs.getString(CONFIG_PG_HOST);
    this.port = configs.getString(CONFIG_PG_PORT);
    this.database = configs.getString(CONFIG_PG_DATABASE);
    this.username = configs.getString(CONFIG_PG_USERNAME);
    this.password = configs.getString(CONFIG_PG_PASSWORD);
    this.poolMaxSize = configs.getInteger(CONFIG_PG_POOL_MAX_SIZE);
  }

  public String getHost() {
    return host;
  }

  public String getPort() {
    return port;
  }

  public String getDatabase() {
    return database;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public int getPoolMaxSize() {
    return poolMaxSize;
  }

  public JsonObject toJSON() {
    return new JsonObject()
      .put("host", this.host)
      .put("port", this.port)
      .put("user", this.username)
      .put("password", this.password)
      .put("database", this.database);
  }
}
