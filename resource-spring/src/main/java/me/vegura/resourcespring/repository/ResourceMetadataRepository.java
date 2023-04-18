package me.vegura.resourcespring.repository;

import me.vegura.resourcespring.domain.ResourceMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceMetadataRepository extends JpaRepository<ResourceMetadata, Long> {
}
