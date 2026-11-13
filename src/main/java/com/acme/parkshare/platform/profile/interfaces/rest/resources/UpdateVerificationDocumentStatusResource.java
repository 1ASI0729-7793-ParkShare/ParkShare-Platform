package com.acme.parkshare.platform.profile.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record UpdateVerificationDocumentStatusResource(@NotBlank String status) {}
