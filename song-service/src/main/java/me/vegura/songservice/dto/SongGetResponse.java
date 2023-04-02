package me.vegura.songservice.dto;

import lombok.*;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data @AllArgsConstructor @NoArgsConstructor @Accessors(chain = true)
public class SongGetResponse extends SongRequest{
    private Long id;
}
