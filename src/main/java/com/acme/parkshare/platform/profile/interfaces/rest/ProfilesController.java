package com.acme.parkshare.platform.profile.interfaces.rest;

import com.acme.parkshare.platform.profile.application.commandservices.ProfileCommandFailure;
import com.acme.parkshare.platform.profile.application.commandservices.ProfileCommandService;
import com.acme.parkshare.platform.profile.application.commands.CreateProfileCommand;
import com.acme.parkshare.platform.profile.application.commands.UpdateProfileCommand;
import com.acme.parkshare.platform.profile.application.queries.GetAllProfilesQuery;
import com.acme.parkshare.platform.profile.application.queries.GetProfileByIdQuery;
import com.acme.parkshare.platform.profile.application.queryservices.ProfileQueryService;
import com.acme.parkshare.platform.profile.domain.model.aggregates.Profile;
import com.acme.parkshare.platform.profile.interfaces.rest.resources.CreateProfileResource;
import com.acme.parkshare.platform.profile.interfaces.rest.resources.ProfileResource;
import com.acme.parkshare.platform.profile.interfaces.rest.resources.UpdateProfileResource;
import com.acme.parkshare.platform.profile.interfaces.rest.transform.ProfileResourceMapper;
import com.acme.parkshare.platform.shared.application.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/profiles", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Endpoints for user profile management")
public class ProfilesController {

    private final ProfileCommandService commandService;
    private final ProfileQueryService queryService;

    public ProfilesController(ProfileCommandService commandService, ProfileQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Create a profile")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Profile created"),
            @ApiResponse(responseCode = "400", description = "Invalid request")})
    public ResponseEntity<ProfileResource> create(@Valid @RequestBody CreateProfileResource resource) {
        var command = new CreateProfileCommand(resource.fullName(), resource.plate(),
                resource.vehicleModel(), resource.vehicleType());
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<Profile, ProfileCommandFailure> s ->
                    ResponseEntity.created(URI.create("/api/v1/profiles/" + s.value().getId()))
                            .body(ProfileResourceMapper.toResource(s.value()));
            case Result.Failure<Profile, ProfileCommandFailure> f -> ResponseEntity.badRequest().build();
        };
    }

    @GetMapping
    @Operation(summary = "Get all profiles")
    @ApiResponse(responseCode = "200", description = "Profiles retrieved")
    public ResponseEntity<List<ProfileResource>> getAll() {
        return ResponseEntity.ok(queryService.handle(new GetAllProfilesQuery())
                .stream().map(ProfileResourceMapper::toResource).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get profile by ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Profile found"),
            @ApiResponse(responseCode = "404", description = "Profile not found")})
    public ResponseEntity<ProfileResource> getById(@PathVariable Long id) {
        return queryService.handle(new GetProfileByIdQuery(id))
                .map(ProfileResourceMapper::toResource).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a profile")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Profile updated"),
            @ApiResponse(responseCode = "404", description = "Profile not found")})
    public ResponseEntity<ProfileResource> update(@PathVariable Long id,
                                                   @Valid @RequestBody UpdateProfileResource resource) {
        var command = new UpdateProfileCommand(id, resource.fullName(), resource.plate(),
                resource.vehicleModel(), resource.vehicleType());
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<Profile, ProfileCommandFailure> s ->
                    ResponseEntity.ok(ProfileResourceMapper.toResource(s.value()));
            case Result.Failure<Profile, ProfileCommandFailure> f ->
                    switch (f.error()) {
                        case ProfileCommandFailure.NotFound x -> ResponseEntity.notFound().build();
                        case ProfileCommandFailure.InvalidVehicleType x -> ResponseEntity.badRequest().build();
                    };
        };
    }
}
