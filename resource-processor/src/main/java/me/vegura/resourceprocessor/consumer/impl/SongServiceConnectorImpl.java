package me.vegura.resourceprocessor.consumer.impl;

import me.vegura.resourceprocessor.consumer.SongServiceConnector;
import me.vegura.resourceprocessor.dto.api.SongCreateMetaRequest;
import me.vegura.resourceprocessor.dto.api.SongCreateMetaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SongServiceConnectorImpl implements SongServiceConnector {

    @Value("${song-service.host}")
    private String songServiceHost;

    @Value("${song-service.port}")
    private String songServicePort;

    private final String POST_SONG_META_URL_PATH = "http://" + songServiceHost + ":" + songServicePort + API_PREFIX;

    @Override
    public void pushData(SongCreateMetaRequest songMetadata) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<SongCreateMetaRequest> request = new HttpEntity<>(songMetadata);
        ResponseEntity<SongCreateMetaResponse> songMetaSaveResponse =
                restTemplate.postForEntity(POST_SONG_META_URL_PATH, request, SongCreateMetaResponse.class);
    }
}
