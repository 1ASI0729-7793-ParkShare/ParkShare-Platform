package com.acme.parkshare.platform.profile.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateVerificationDocumentResource(
        @NotNull Long profileId,
        @NotBlank String type,
        String fileName
) {}
