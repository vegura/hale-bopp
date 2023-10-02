package me.vegura.resourceprocessor.consumer.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.vegura.resourceprocessor.consumer.SongServiceConnector;
import me.vegura.resourceprocessor.dto.api.SongCreateMetaRequest;
import me.vegura.resourceprocessor.dto.api.SongCreateMetaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class SongServiceConnectorImpl implements SongServiceConnector {

    private static final String SONG_SERVICE_LINK = "Song-Service";

    private final RestTemplate restTemplate;

    @Override
    public void pushData(SongCreateMetaRequest songMetadata) {
        String path = "http://" + SONG_SERVICE_LINK + API_PREFIX;
        ObjectMapper objectMapper = new ObjectMapper();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        restTemplate.getMessageConverters().add(0, converter);

        HttpEntity<SongCreateMetaRequest> request = new HttpEntity<>(songMetadata);

        ResponseEntity<SongCreateMetaResponse> songMetaSaveResponse = restTemplate.postForEntity(path, request, SongCreateMetaResponse.class);
    }
}
