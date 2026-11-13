package com.acme.parkshare.platform.reputationnotifications.interfaces.rest;

import com.acme.parkshare.platform.reputationnotifications.application.commands.CreateNotificationCommand;
import com.acme.parkshare.platform.reputationnotifications.application.commands.MarkNotificationAsReadCommand;
import com.acme.parkshare.platform.reputationnotifications.application.commandservices.NotificationCommandFailure;
import com.acme.parkshare.platform.reputationnotifications.application.commandservices.NotificationCommandService;
import com.acme.parkshare.platform.reputationnotifications.application.queries.GetAllNotificationsQuery;
import com.acme.parkshare.platform.reputationnotifications.application.queries.GetNotificationByIdQuery;
import com.acme.parkshare.platform.reputationnotifications.application.queryservices.NotificationQueryService;
import com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates.Notification;
import com.acme.parkshare.platform.reputationnotifications.interfaces.rest.resources.CreateNotificationResource;
import com.acme.parkshare.platform.reputationnotifications.interfaces.rest.resources.NotificationResource;
import com.acme.parkshare.platform.reputationnotifications.interfaces.rest.transform.NotificationResourceMapper;
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
@RequestMapping(value = "/api/v1/notifications", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Notifications", description = "Endpoints for user notifications")
public class NotificationsController {

    private final NotificationCommandService commandService;
    private final NotificationQueryService queryService;

    public NotificationsController(NotificationCommandService commandService,
                                   NotificationQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Create a notification")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Notification created"),
            @ApiResponse(responseCode = "400", description = "Invalid payload")
    })
    public ResponseEntity<NotificationResource> create(@Valid @RequestBody CreateNotificationResource resource) {
        var command = new CreateNotificationCommand(
                resource.recipientId(),
                resource.type(),
                resource.title(),
                resource.message(),
                resource.relatedEntityType(),
                resource.relatedEntityId()
        );
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<Notification, NotificationCommandFailure> s ->
                    ResponseEntity.created(URI.create("/api/v1/notifications/" + s.value().getId()))
                            .body(NotificationResourceMapper.toResource(s.value()));
            case Result.Failure<Notification, NotificationCommandFailure> f ->
                    ResponseEntity.badRequest().build();
        };
    }

    @GetMapping
    @Operation(summary = "Get all notifications")
    @ApiResponse(responseCode = "200", description = "Notifications retrieved")
    public ResponseEntity<List<NotificationResource>> getAll() {
        var list = queryService.handle(new GetAllNotificationsQuery())
                .stream().map(NotificationResourceMapper::toResource).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get notification by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notification found"),
            @ApiResponse(responseCode = "404", description = "Notification not found")
    })
    public ResponseEntity<NotificationResource> getById(@PathVariable Long id) {
        return queryService.handle(new GetNotificationByIdQuery(id))
                .map(NotificationResourceMapper::toResource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mark notification as read")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notification marked as read"),
            @ApiResponse(responseCode = "404", description = "Notification not found")
    })
    public ResponseEntity<NotificationResource> markAsRead(@PathVariable Long id) {
        var result = commandService.handle(new MarkNotificationAsReadCommand(id));
        return switch (result) {
            case Result.Success<Notification, NotificationCommandFailure> s ->
                    ResponseEntity.ok(NotificationResourceMapper.toResource(s.value()));
            case Result.Failure<Notification, NotificationCommandFailure> f ->
                    ResponseEntity.notFound().build();
        };
    }
}
