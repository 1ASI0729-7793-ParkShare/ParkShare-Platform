package com.acme.parkshare.platform.profile.interfaces.rest.resources;

import java.time.Instant;

public record VerificationDocumentResource(Long id, Long profileId, String type, String status,
                                           String fileName, Instant createdAt, Instant updatedAt) {}
