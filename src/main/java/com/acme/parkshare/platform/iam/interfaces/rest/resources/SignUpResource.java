package com.acme.parkshare.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Resource received to register a new IAM user.
 */
@Schema(
    name = "SignUpRequest",
    description = "Public registration. The user picks DRIVER (default) or OWNER; ADMIN cannot be requested.",
    example = "{\"username\": \"john.doe\", \"password\": \"SecurePass123!\", \"role\": \"OWNER\"}"
)
@JsonIgnoreProperties(ignoreUnknown = false)
public record SignUpResource(
    @Schema(
        description = "Desired username",
        example = "john.doe",
        minLength = 3,
        maxLength = 50
    )
    @NotBlank @Size(min = 3, max = 50) String username,

    @Schema(
        description = "User password (minimum 8 characters)",
        example = "SecurePass123!",
        minLength = 8,
        maxLength = 255
    )
    @NotBlank @Size(min = 8, max = 255) String password,
    @Schema(
        description = "Account type chosen at registration. ADMIN cannot be requested.",
        example = "OWNER",
        allowableValues = {"DRIVER", "OWNER"}
    )
    @jakarta.validation.constraints.Pattern(regexp = "DRIVER|OWNER", message = "role must be DRIVER or OWNER")
    String role
) {
}
