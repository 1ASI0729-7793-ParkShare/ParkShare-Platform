package com.acme.parkshare.platform.emergencyalerts.interfaces.rest;

import com.acme.parkshare.platform.emergencyalerts.application.commands.CreateEmergencyAlertCommand;
import com.acme.parkshare.platform.emergencyalerts.application.commands.ResolveEmergencyAlertCommand;
import com.acme.parkshare.platform.emergencyalerts.application.commandservices.EmergencyAlertCommandFailure;
import com.acme.parkshare.platform.emergencyalerts.application.commandservices.EmergencyAlertCommandService;
import com.acme.parkshare.platform.emergencyalerts.application.queries.GetAllEmergencyAlertsQuery;
import com.acme.parkshare.platform.emergencyalerts.application.queries.GetEmergencyAlertByIdQuery;
import com.acme.parkshare.platform.emergencyalerts.application.queryservices.EmergencyAlertQueryService;
import com.acme.parkshare.platform.emergencyalerts.domain.model.aggregates.EmergencyAlert;
import com.acme.parkshare.platform.emergencyalerts.interfaces.rest.resources.CreateEmergencyAlertResource;
import com.acme.parkshare.platform.emergencyalerts.interfaces.rest.resources.EmergencyAlertResource;
import com.acme.parkshare.platform.emergencyalerts.interfaces.rest.transform.EmergencyAlertResourceMapper;
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
@RequestMapping(value = "/api/v1/emergency-alerts", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Emergency Alerts", description = "Endpoints for emergency alert notifications")
public class EmergencyAlertsController {

    private final EmergencyAlertCommandService commandService;
    private final EmergencyAlertQueryService queryService;

    public EmergencyAlertsController(EmergencyAlertCommandService commandService,
                                     EmergencyAlertQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Create an emergency alert")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Emergency alert created"),
            @ApiResponse(responseCode = "400", description = "Invalid payload")
    })
    public ResponseEntity<EmergencyAlertResource> create(@Valid @RequestBody CreateEmergencyAlertResource resource) {
        var command = new CreateEmergencyAlertCommand(
                resource.reporterId(),
                resource.parkingListingId(),
                resource.ownerId(),
                resource.latitude(),
                resource.longitude(),
                resource.description()
        );
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<EmergencyAlert, EmergencyAlertCommandFailure> s ->
                    ResponseEntity.created(URI.create("/api/v1/emergency-alerts/" + s.value().getId()))
                            .body(EmergencyAlertResourceMapper.toResource(s.value()));
            case Result.Failure<EmergencyAlert, EmergencyAlertCommandFailure> f ->
                    ResponseEntity.badRequest().build();
        };
    }

    @GetMapping
    @Operation(summary = "Get all emergency alerts")
    @ApiResponse(responseCode = "200", description = "Emergency alerts retrieved")
    public ResponseEntity<List<EmergencyAlertResource>> getAll() {
        var list = queryService.handle(new GetAllEmergencyAlertsQuery())
                .stream().map(EmergencyAlertResourceMapper::toResource).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get emergency alert by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Emergency alert found"),
            @ApiResponse(responseCode = "404", description = "Emergency alert not found")
    })
    public ResponseEntity<EmergencyAlertResource> getById(@PathVariable Long id) {
        return queryService.handle(new GetEmergencyAlertByIdQuery(id))
                .map(EmergencyAlertResourceMapper::toResource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Resolve emergency alert")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Emergency alert resolved"),
            @ApiResponse(responseCode = "404", description = "Emergency alert not found")
    })
    public ResponseEntity<EmergencyAlertResource> resolve(@PathVariable Long id) {
        var result = commandService.handle(new ResolveEmergencyAlertCommand(id));
        return switch (result) {
            case Result.Success<EmergencyAlert, EmergencyAlertCommandFailure> s ->
                    ResponseEntity.ok(EmergencyAlertResourceMapper.toResource(s.value()));
            case Result.Failure<EmergencyAlert, EmergencyAlertCommandFailure> f ->
                    ResponseEntity.notFound().build();
        };
    }
}
