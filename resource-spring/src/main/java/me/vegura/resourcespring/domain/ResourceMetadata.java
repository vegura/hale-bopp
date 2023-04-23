package me.vegura.resourcespring.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity @Table(name = "resource_meta")
@Builder @NoArgsConstructor @Data @AllArgsConstructor
public class ResourceMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID resourceKey;

    /*
     * SHA256
     */
    private String signature;

    private Integer fileSize;
}
