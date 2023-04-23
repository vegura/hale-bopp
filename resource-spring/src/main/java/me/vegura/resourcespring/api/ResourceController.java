package me.vegura.resourcespring.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.vegura.resourcespring.dto.ResourceCreationRes;
import me.vegura.resourcespring.dto.ResourceResponse;
import me.vegura.resourcespring.service.ResourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor @Slf4j
@RestController
@RequestMapping("/resources")
public class ResourceController {

    private final ResourceService resourceService;

    @PostMapping
    public ResponseEntity<ResourceCreationRes> postResource(InputStream dataStream) throws IOException {
        var fileContents = dataStream.readAllBytes();
        return ResponseEntity.ok(resourceService.createResource(fileContents));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getResourceById(@PathVariable("id") Long id) {
        Optional<ResourceResponse> maybeResource = resourceService.getResourceBy(id);
        if (maybeResource.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(maybeResource.get());
    }

    @GetMapping("{id}/raw")
    public @ResponseBody byte[] getRawDataById(@PathVariable("id") Long id) {
        Optional<ResourceResponse> maybeResource = resourceService.getResourceBy(id);
        if (maybeResource.isEmpty()) {
            throw new RuntimeException("Unable to find resource by this id");
        }

        return maybeResource.get().getResourceData();
    }

    @DeleteMapping
    public List<Long> deleteResources(@RequestParam("ids") String ids) {
        List<Long> idsToDelete = Arrays.stream(ids.split(",")).map(Long::parseLong).toList();
        return resourceService.deleteBy(idsToDelete);
    }
}
