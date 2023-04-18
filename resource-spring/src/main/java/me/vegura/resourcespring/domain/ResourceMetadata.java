package me.vegura.resourcespring.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Builder @NoArgsConstructor @Data @AllArgsConstructor
public class ResourceMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private UUID resourceKey;

    /*
     * SHA256
     */
    private String signature;

    private Integer fileSize;
}
