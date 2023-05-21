package me.vegura.resourceprocessor.consumer.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.vegura.resourceprocessor.consumer.SongServiceConnector;
import me.vegura.resourceprocessor.dto.api.SongCreateMetaRequest;
import me.vegura.resourceprocessor.dto.api.SongCreateMetaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SongServiceConnectorImpl implements SongServiceConnector {

    @Value("${song-service.host}")
    private String songServiceHost;

    @Value("${song-service.port}")
    private String songServicePort;

    @Override
    public void pushData(SongCreateMetaRequest songMetadata) {
        String path = "http://" + songServiceHost + ":" + songServicePort + API_PREFIX;
        RestTemplate restTemplate = new RestTemplate();

        new ObjectMapper();
        HttpEntity<SongCreateMetaRequest> request = new HttpEntity<>(songMetadata);
//        ResponseEntity<SongCreateMetaResponse> songMetaSaveResponse =
                restTemplate.postForEntity(path, request, String.class);
    }
}
