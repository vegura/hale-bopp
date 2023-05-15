package me.vegura.resourceprocessor.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import me.vegura.resourceprocessor.service.ResourceProcessingService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = "resource.upload.notification")
@Slf4j @RequiredArgsConstructor
public class ResourceConsumer {

    private final ResourceProcessingService resourceService;

    @RabbitHandler
    public void handleResourceCreateNotification(final String message) {
        log.info("Received resource creation request -> {}", message);
        ResourceQueueRequest resourceCreationRequest = tryMappingRequest(message);

    }

    private ResourceQueueRequest tryMappingRequest(String requestMessage) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(requestMessage, ResourceQueueRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public record ResourceQueueRequest(Long id) {

    }
}
