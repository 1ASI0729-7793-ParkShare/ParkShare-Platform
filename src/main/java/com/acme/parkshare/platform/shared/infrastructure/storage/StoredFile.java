package com.acme.parkshare.platform.shared.infrastructure.storage;

/** A file saved by a {@link FileStorageService}. */
public record StoredFile(String fileName, String contentType, long size) {}
