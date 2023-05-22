package me.vegura.resourceprocessor.dto.api;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResourceDTOResponse implements Serializable {
    private Long id;
    private byte[] resourceData;
}
