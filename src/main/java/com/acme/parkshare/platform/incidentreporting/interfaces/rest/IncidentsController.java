package com.acme.parkshare.platform.incidentreporting.interfaces.rest;

import com.acme.parkshare.platform.incidentreporting.application.commandservices.IncidentCommandFailure;
import com.acme.parkshare.platform.incidentreporting.application.commandservices.IncidentCommandService;
import com.acme.parkshare.platform.incidentreporting.application.commands.CreateIncidentCommand;
import com.acme.parkshare.platform.incidentreporting.application.commands.UpdateIncidentStatusCommand;
import com.acme.parkshare.platform.incidentreporting.application.queries.GetAllIncidentsQuery;
import com.acme.parkshare.platform.incidentreporting.application.queries.GetIncidentByIdQuery;
import com.acme.parkshare.platform.incidentreporting.application.queryservices.IncidentQueryService;
import com.acme.parkshare.platform.incidentreporting.domain.model.aggregates.Incident;
import com.acme.parkshare.platform.incidentreporting.interfaces.rest.resources.CreateIncidentResource;
import com.acme.parkshare.platform.incidentreporting.interfaces.rest.resources.IncidentResource;
import com.acme.parkshare.platform.incidentreporting.interfaces.rest.resources.UpdateIncidentStatusResource;
import com.acme.parkshare.platform.incidentreporting.interfaces.rest.transform.IncidentResourceMapper;
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
@RequestMapping(value = "/api/v1/incidents", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Incidents", description = "Endpoints for incident reporting")
public class IncidentsController {

    private final IncidentCommandService commandService;
    private final IncidentQueryService queryService;

    public IncidentsController(IncidentCommandService commandService, IncidentQueryService queryService) {
        this.commandService = commandService; this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Report an incident")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Incident reported"),
            @ApiResponse(responseCode = "400", description = "Invalid request")})
    public ResponseEntity<IncidentResource> create(@Valid @RequestBody CreateIncidentResource resource) {
        var command = new CreateIncidentCommand(resource.reporterId(), resource.reporterName(),
                resource.parkingListingId(), resource.ownerId(), resource.spaceName(),
                resource.spaceAddress(), resource.incidentTypes(), resource.description(), resource.photos());
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<Incident, IncidentCommandFailure> s ->
                    ResponseEntity.created(URI.create("/api/v1/incidents/" + s.value().getId()))
                            .body(IncidentResourceMapper.toResource(s.value()));
            case Result.Failure<Incident, IncidentCommandFailure> f -> ResponseEntity.badRequest().build();
        };
    }

    @GetMapping
    @Operation(summary = "Get all incidents")
    @ApiResponse(responseCode = "200", description = "Incidents retrieved")
    public ResponseEntity<List<IncidentResource>> getAll() {
        return ResponseEntity.ok(queryService.handle(new GetAllIncidentsQuery())
                .stream().map(IncidentResourceMapper::toResource).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get incident by ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Incident found"),
            @ApiResponse(responseCode = "404", description = "Incident not found")})
    public ResponseEntity<IncidentResource> getById(@PathVariable Long id) {
        return queryService.handle(new GetIncidentByIdQuery(id))
                .map(IncidentResourceMapper::toResource).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update incident status")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Incident updated"),
            @ApiResponse(responseCode = "404", description = "Incident not found")})
    public ResponseEntity<IncidentResource> updateStatus(@PathVariable Long id,
            @Valid @RequestBody UpdateIncidentStatusResource resource) {
        var result = commandService.handle(new UpdateIncidentStatusCommand(id, resource.status()));
        return switch (result) {
            case Result.Success<Incident, IncidentCommandFailure> s ->
                    ResponseEntity.ok(IncidentResourceMapper.toResource(s.value()));
            case Result.Failure<Incident, IncidentCommandFailure> f ->
                    switch (f.error()) {
                        case IncidentCommandFailure.NotFound x -> ResponseEntity.notFound().build();
                        case IncidentCommandFailure.InvalidStatus x -> ResponseEntity.badRequest().build();
                    };
        };
    }
}
