package me.vegura.resourceprocessor.consumer;

import me.vegura.resourceprocessor.dto.api.SongCreateMetaRequest;

public interface SongServiceConnector {

    String API_PREFIX = "/songs";

    void pushData(SongCreateMetaRequest songMetadata);
}
