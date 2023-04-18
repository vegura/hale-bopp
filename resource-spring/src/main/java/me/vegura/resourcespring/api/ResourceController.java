package me.vegura.resourcespring.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.vegura.resourcespring.dto.ResourceCreationRes;
import me.vegura.resourcespring.service.ResourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor @Slf4j
@RestController
@RequestMapping("/resources")
public class ResourceController {

    private final ResourceService resourceService;

    @PostMapping
    public ResponseEntity<ResourceCreationRes> postResource(@RequestBody byte[] file) {
        log.info("Fetching file -> ", file);
        return ResponseEntity.ok(resourceService.createResource(file));
    }
}
