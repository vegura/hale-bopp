package me.vegura.resource.database.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;

@DataObject(generateConverter = true)
@JsonPropertyOrder({
  "id",
  "created",
  "updated",
  "signature",
  "size_bytes",
  "description",
  "bucket",
  "path"
})
public class Resource {
  @JsonProperty("id")
  private Integer id;

  @JsonProperty("created")
  private LocalDateTime created;

  @JsonProperty("updated")
  private LocalDateTime updated;

  @JsonProperty("signature")
  private String signature;

  @JsonProperty("size_bytes")
  private Long sizeBytes;

  @JsonProperty("description")
  private String description;

  @JsonProperty("bucket")
  private String bucket;

  @JsonProperty("path")
  private String path;

  public Resource() {

  }

  public Resource(Integer id, LocalDateTime created, LocalDateTime updated, String signature, Long sizeBytes, String description, String bucket, String path) {
    this.id = id;
    this.created = created;
    this.updated = updated;
    this.signature = signature;
    this.sizeBytes = sizeBytes;
    this.description = description;
    this.bucket = bucket;
    this.path = path;
  }

  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    ResourceConverter.fromJson(json, this);
    return json;
  }



  public Resource(JsonObject from) {
    this.id = from.getInteger("id");
    this.signature = from.getString("signature");
    this.bucket = from.getString("bucket");
    this.sizeBytes = from.getLong("size_bytes");
    this.description = from.getString("description");
  }
}
