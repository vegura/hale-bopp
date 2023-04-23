package me.vegura.resourcespring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data @Accessors(chain = true) @Builder @NoArgsConstructor @AllArgsConstructor
public class ResourceResponse {
    private Long id;
    private byte[] resourceData;
}
