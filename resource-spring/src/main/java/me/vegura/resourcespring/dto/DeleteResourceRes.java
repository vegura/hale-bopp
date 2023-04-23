package me.vegura.resourcespring.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data @Accessors(chain = true)
public class DeleteResourceRes {
    private List<Long> ids;
}
