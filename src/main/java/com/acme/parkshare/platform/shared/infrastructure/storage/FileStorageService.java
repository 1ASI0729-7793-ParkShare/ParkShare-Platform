package com.acme.parkshare.platform.shared.infrastructure.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * Where uploaded images live. The local-disk implementation is enough for development; production
 * hosts with ephemeral disks should swap it for an object store (S3, Cloudinary, ...) without
 * touching the controllers.
 */
public interface FileStorageService {

    /** Validates and saves an image; throws {@link IllegalArgumentException} if it is not an accepted image. */
    StoredFile store(MultipartFile file);

    Optional<Resource> load(String fileName);

    /** Content type to serve a stored file with, derived from its name. */
    String contentTypeOf(String fileName);
}
