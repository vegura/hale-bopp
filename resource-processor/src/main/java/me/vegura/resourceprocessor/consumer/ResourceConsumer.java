package me.vegura.resourceprocessor.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import me.vegura.resourceprocessor.service.ResourceProcessingService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "resource.upload.notification", id = "listener")
@Slf4j @RequiredArgsConstructor
public class ResourceConsumer {

    private final ResourceProcessingService resourceService;

    @RabbitHandler
    public void handleResourceCreateNotification(String message) {
        log.info("Received resource creation request -> {}", message);
        ResourceQueueRequest resourceCreationRequest = tryMappingRequest(message);
        resourceService.getResourceAndParseMetadata(resourceCreationRequest.getId());
    }

    private ResourceQueueRequest tryMappingRequest(String requestMessage) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(requestMessage, ResourceQueueRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Data @AllArgsConstructor @NoArgsConstructor @Builder
    public static class ResourceQueueRequest {
        private Long id;
    }
}
