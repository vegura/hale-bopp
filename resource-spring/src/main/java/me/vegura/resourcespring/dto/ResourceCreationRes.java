package me.vegura.resourcespring.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data @Accessors(chain = true)
public class ResourceCreationRes implements Serializable {
    private Long id;
}
