package com.acme.parkshare.platform.booking.interfaces.rest;

import com.acme.parkshare.platform.booking.application.commandservices.ReservationCommandFailure;
import com.acme.parkshare.platform.booking.application.commandservices.ReservationCommandService;
import com.acme.parkshare.platform.booking.application.queries.GetAllReservationsQuery;
import com.acme.parkshare.platform.booking.application.queries.GetReservationByIdQuery;
import com.acme.parkshare.platform.booking.application.queryservices.ReservationQueryService;
import com.acme.parkshare.platform.booking.domain.model.aggregates.Reservation;
import com.acme.parkshare.platform.booking.interfaces.rest.resources.CreateReservationResource;
import com.acme.parkshare.platform.booking.interfaces.rest.resources.ReservationResource;
import com.acme.parkshare.platform.booking.interfaces.rest.resources.UpdateReservationStatusResource;
import com.acme.parkshare.platform.booking.interfaces.rest.transform.CreateReservationCommandMapper;
import com.acme.parkshare.platform.booking.interfaces.rest.transform.ReservationResourceMapper;
import com.acme.parkshare.platform.booking.application.commands.UpdateReservationStatusCommand;
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
@RequestMapping(value = "/api/v1/bookings", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Bookings", description = "Endpoints for reservation management")
public class ReservationsController {

    private final ReservationCommandService commandService;
    private final ReservationQueryService queryService;

    public ReservationsController(ReservationCommandService commandService,
                                  ReservationQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Create a reservation")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reservation created"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<ReservationResource> create(@Valid @RequestBody CreateReservationResource resource) {
        var command = CreateReservationCommandMapper.toCommand(resource);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<Reservation, ReservationCommandFailure> s ->
                    ResponseEntity.created(URI.create("/api/v1/bookings/" + s.value().getId()))
                            .body(ReservationResourceMapper.toResource(s.value()));
            case Result.Failure<Reservation, ReservationCommandFailure> f ->
                    ResponseEntity.badRequest().build();
        };
    }

    @GetMapping
    @Operation(summary = "Get all reservations")
    @ApiResponse(responseCode = "200", description = "Reservations retrieved")
    public ResponseEntity<List<ReservationResource>> getAll() {
        var reservations = queryService.handle(new GetAllReservationsQuery())
                .stream().map(ReservationResourceMapper::toResource).toList();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get reservation by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservation found"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    public ResponseEntity<ReservationResource> getById(@PathVariable Long id) {
        return queryService.handle(new GetReservationByIdQuery(id))
                .map(ReservationResourceMapper::toResource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update reservation status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservation updated"),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "400", description = "Invalid status transition")
    })
    public ResponseEntity<ReservationResource> updateStatus(@PathVariable Long id,
                                                             @Valid @RequestBody UpdateReservationStatusResource resource) {
        var command = new UpdateReservationStatusCommand(id, resource.status());
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<Reservation, ReservationCommandFailure> s ->
                    ResponseEntity.ok(ReservationResourceMapper.toResource(s.value()));
            case Result.Failure<Reservation, ReservationCommandFailure> f ->
                    switch (f.error()) {
                        case ReservationCommandFailure.NotFound x -> ResponseEntity.notFound().build();
                        case ReservationCommandFailure.InvalidStatus x -> ResponseEntity.badRequest().build();
                    };
        };
    }
}
