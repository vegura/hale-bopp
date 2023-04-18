package me.vegura.resourcespring.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data @Accessors(chain = true)
public class ResourceCreationRes {
    private Long id;
}
