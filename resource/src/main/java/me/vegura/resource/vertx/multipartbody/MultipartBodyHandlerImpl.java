package me.vegura.resource.vertx.multipartbody;

import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultipartBodyHandlerImpl implements MultipartBodyHandler {

  private static final Logger logger = LoggerFactory.getLogger(MultipartBodyHandlerImpl.class);

  private static final String BODY_HANDLER = "__body-handled";

  private long bodyLimit = DEFAULT_BODY_LIMIT;
  private boolean handleFileUploads;
  private String uploadsDir;
  private boolean mergeFormAttributes = DEFAULT_MERGE_FORM_ATTRIBUTES;
  private boolean deleteUploadedFilesOnEnd = DEFAULT_DELETE_UPLOADED_FILES_ON_END;
  private boolean isPreallocateBodyBuffer = DEFAULT_PREALLOCATE_BODY_BUFFER;
  private static final int DEFAULT_INITIAL_BODY_BUFFER_SIZE = 1024; //bytes

  public MultipartBodyHandlerImpl() {
    this(true, DEFAULT_UPLOADS_DIRECTORY);
  }

  public MultipartBodyHandlerImpl(boolean handleFileUploads) {
    this(handleFileUploads, DEFAULT_UPLOADS_DIRECTORY);
  }

  public MultipartBodyHandlerImpl(String uploadsDir) {
    this(true, uploadsDir);
  }

  private MultipartBodyHandlerImpl(boolean handleFileUploads, String uploadDirectory) {
    this.handleFileUploads = handleFileUploads;
    setUploadsDirectory(uploadDirectory);
  }

  @Override
  public MultipartBodyHandler setHandleFileUploads(boolean handleFileUploads) {
    return null;
  }

  @Override
  public MultipartBodyHandler setBodyLimit(long bodyLimit) {
    return null;
  }

  @Override
  public MultipartBodyHandler setUploadsDirectory(String uploadsDirectory) {
    return null;
  }

  @Override
  public MultipartBodyHandler setMergeFormAttributes(boolean mergeFormAttributes) {
    return null;
  }

  @Override
  public MultipartBodyHandler setDeleteUploadedFilesOnEnd(boolean deleteUploadedFilesOnEnd) {
    return null;
  }

  @Override
  public MultipartBodyHandler setPreallocateBodyBuffer(boolean isPreallocateBodyBuffer) {
    return null;
  }

  @Override
  public void handle(RoutingContext context) {
    HttpServerRequest request = context.request();

    if (request.headers().contains(HttpHeaders.UPGRADE, HttpHeaders.WEBSOCKET, true)) {
      context.next();
      return;
    }


  }
}
