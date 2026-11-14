package com.acme.parkshare.platform.shared.interfaces.rest;

import com.acme.parkshare.platform.shared.infrastructure.storage.FileStorageService;
import com.acme.parkshare.platform.shared.interfaces.rest.resources.StoredFileResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Duration;

@RestController
@RequestMapping("/api/v1/files")
@Tag(name = "Files", description = "Upload and serve photos (parking spaces, incident reports)")
public class FilesController {

    private final FileStorageService storage;

    public FilesController(FileStorageService storage) {
        this.storage = storage;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Upload an image (JPEG, PNG or WebP, up to 5 MB)",
            description = "Returns the public URL to save in the photos of a listing or an incident.")
    public ResponseEntity<StoredFileResource> upload(@RequestParam("file") MultipartFile file) {
        var stored = storage.store(file);
        var url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/files/").path(stored.fileName()).toUriString();
        return ResponseEntity.created(URI.create(url))
                .body(new StoredFileResource(url, stored.fileName(), stored.contentType(), stored.size()));
    }

    @GetMapping("/{fileName}")
    @Operation(summary = "Get an uploaded image")
    public ResponseEntity<Resource> get(@PathVariable String fileName) {
        return storage.load(fileName)
                .map(resource -> ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(storage.contentTypeOf(fileName)))
                        .cacheControl(CacheControl.maxAge(Duration.ofDays(30)).cachePublic())
                        .body(resource))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
