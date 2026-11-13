package com.acme.parkshare.platform.parkingspacemanagement.interfaces.rest;

import com.acme.parkshare.platform.parkingspacemanagement.application.commands.DeleteParkingSpaceCommand;
import com.acme.parkshare.platform.parkingspacemanagement.application.commandservices.ParkingSpaceCommandFailure;
import com.acme.parkshare.platform.parkingspacemanagement.application.commandservices.ParkingSpaceCommandService;
import com.acme.parkshare.platform.parkingspacemanagement.application.queries.GetAllParkingSpacesQuery;
import com.acme.parkshare.platform.parkingspacemanagement.application.queries.GetParkingSpaceByIdQuery;
import com.acme.parkshare.platform.parkingspacemanagement.application.queryservices.ParkingSpaceQueryService;
import com.acme.parkshare.platform.parkingspacemanagement.domain.model.aggregates.ParkingSpace;
import com.acme.parkshare.platform.parkingspacemanagement.interfaces.rest.resources.CreateParkingSpaceResource;
import com.acme.parkshare.platform.parkingspacemanagement.interfaces.rest.resources.ParkingSpaceResource;
import com.acme.parkshare.platform.parkingspacemanagement.interfaces.rest.resources.UpdateParkingSpaceResource;
import com.acme.parkshare.platform.parkingspacemanagement.interfaces.rest.transform.CreateParkingSpaceCommandMapper;
import com.acme.parkshare.platform.parkingspacemanagement.interfaces.rest.transform.ParkingSpaceResourceMapper;
import com.acme.parkshare.platform.parkingspacemanagement.interfaces.rest.transform.UpdateParkingSpaceCommandMapper;
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
@RequestMapping(value = "/api/v1/parking-spaces", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Parking Spaces", description = "Endpoints for parking space owner management")
public class ParkingSpacesController {

    private final ParkingSpaceCommandService commandService;
    private final ParkingSpaceQueryService queryService;

    public ParkingSpacesController(ParkingSpaceCommandService commandService,
                                   ParkingSpaceQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Create a new parking space")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Parking space created"),
            @ApiResponse(responseCode = "400", description = "Invalid payload")
    })
    public ResponseEntity<ParkingSpaceResource> create(@Valid @RequestBody CreateParkingSpaceResource resource) {
        var command = CreateParkingSpaceCommandMapper.toCommand(resource);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<ParkingSpace, ParkingSpaceCommandFailure> s ->
                    ResponseEntity.created(URI.create("/api/v1/parking-spaces/" + s.value().getId()))
                            .body(ParkingSpaceResourceMapper.toResource(s.value()));
            case Result.Failure<ParkingSpace, ParkingSpaceCommandFailure> f ->
                    ResponseEntity.badRequest().build();
        };
    }

    @GetMapping
    @Operation(summary = "Get all parking spaces")
    @ApiResponse(responseCode = "200", description = "Parking spaces retrieved")
    public ResponseEntity<List<ParkingSpaceResource>> getAll() {
        var list = queryService.handle(new GetAllParkingSpacesQuery())
                .stream().map(ParkingSpaceResourceMapper::toResource).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get parking space by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Parking space found"),
            @ApiResponse(responseCode = "404", description = "Parking space not found")
    })
    public ResponseEntity<ParkingSpaceResource> getById(@PathVariable Long id) {
        return queryService.handle(new GetParkingSpaceByIdQuery(id))
                .map(ParkingSpaceResourceMapper::toResource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update parking space")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Parking space updated"),
            @ApiResponse(responseCode = "404", description = "Parking space not found"),
            @ApiResponse(responseCode = "400", description = "Invalid update payload")
    })
    public ResponseEntity<ParkingSpaceResource> update(@PathVariable Long id,
                                                        @Valid @RequestBody UpdateParkingSpaceResource resource) {
        var command = UpdateParkingSpaceCommandMapper.toCommand(id, resource);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<ParkingSpace, ParkingSpaceCommandFailure> s ->
                    ResponseEntity.ok(ParkingSpaceResourceMapper.toResource(s.value()));
            case Result.Failure<ParkingSpace, ParkingSpaceCommandFailure> f ->
                    switch (f.error()) {
                        case ParkingSpaceCommandFailure.NotFound x -> ResponseEntity.notFound().build();
                        case ParkingSpaceCommandFailure.InvalidState x -> ResponseEntity.badRequest().build();
                    };
        };
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete parking space")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Parking space deleted"),
            @ApiResponse(responseCode = "404", description = "Parking space not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var result = commandService.handle(new DeleteParkingSpaceCommand(id));
        return switch (result) {
            case Result.Success<Void, ParkingSpaceCommandFailure> s -> ResponseEntity.noContent().build();
            case Result.Failure<Void, ParkingSpaceCommandFailure> f -> ResponseEntity.notFound().build();
        };
    }
}
