package me.vegura.songservice.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SongDeleteResponse implements Serializable {
    private List<Long> ids = new ArrayList<>();

    public void addId(Long id) {
        ids.add(id);
    }
}
