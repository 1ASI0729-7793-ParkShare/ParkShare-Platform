package com.acme.parkshare.platform.shared.interfaces.rest.resources;

public record StoredFileResource(String url, String fileName, String contentType, long size) {}
