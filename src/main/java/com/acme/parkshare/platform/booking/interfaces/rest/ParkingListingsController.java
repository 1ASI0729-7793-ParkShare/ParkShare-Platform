package com.acme.parkshare.platform.booking.interfaces.rest;

import com.acme.parkshare.platform.booking.application.commandservices.ParkingListingCommandFailure;
import com.acme.parkshare.platform.booking.application.commandservices.ParkingListingCommandService;
import com.acme.parkshare.platform.booking.application.queries.GetAllParkingListingsQuery;
import com.acme.parkshare.platform.booking.application.queries.GetParkingListingByIdQuery;
import com.acme.parkshare.platform.booking.application.queryservices.ParkingListingQueryService;
import com.acme.parkshare.platform.booking.domain.model.aggregates.ParkingListing;
import com.acme.parkshare.platform.booking.interfaces.rest.resources.CreateParkingListingResource;
import com.acme.parkshare.platform.booking.interfaces.rest.resources.ParkingListingResource;
import com.acme.parkshare.platform.booking.interfaces.rest.resources.UpdateParkingListingResource;
import com.acme.parkshare.platform.booking.interfaces.rest.transform.CreateParkingListingCommandMapper;
import com.acme.parkshare.platform.booking.interfaces.rest.transform.ParkingListingResourceMapper;
import com.acme.parkshare.platform.booking.interfaces.rest.transform.UpdateParkingListingCommandMapper;
import com.acme.parkshare.platform.shared.application.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.acme.parkshare.platform.shared.interfaces.rest.transform.JsonText;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/parking-listings", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Parking Listings", description = "Endpoints for parking listings management")
public class ParkingListingsController {

    private final ParkingListingCommandService commandService;
    private final ParkingListingQueryService queryService;

    public ParkingListingsController(ParkingListingCommandService commandService,
                                     ParkingListingQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Create a parking listing")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Parking listing created"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<ParkingListingResource> create(@Valid @RequestBody CreateParkingListingResource resource) {
        var command = CreateParkingListingCommandMapper.toCommand(resource);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<ParkingListing, ParkingListingCommandFailure> s ->
                    ResponseEntity.created(URI.create("/api/v1/parking-listings/" + s.value().getId()))
                            .body(ParkingListingResourceMapper.toResource(s.value()));
            case Result.Failure<ParkingListing, ParkingListingCommandFailure> f ->
                    ResponseEntity.badRequest().build();
        };
    }

    @GetMapping
    @Operation(summary = "Get all parking listings")
    @ApiResponse(responseCode = "200", description = "Parking listings retrieved")
    public ResponseEntity<List<ParkingListingResource>> getAll(@RequestParam(required = false) Long ownerId) {
        var listings = queryService.handle(new GetAllParkingListingsQuery())
                .stream()
                .filter(listing -> ownerId == null || ownerId.equals(listing.getOwnerId()))
                .map(ParkingListingResourceMapper::toResource).toList();
        return ResponseEntity.ok(listings);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get parking listing by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Parking listing found"),
            @ApiResponse(responseCode = "404", description = "Parking listing not found")
    })
    public ResponseEntity<ParkingListingResource> getById(@PathVariable Long id) {
        return queryService.handle(new GetParkingListingByIdQuery(id))
                .map(ParkingListingResourceMapper::toResource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Partially update a parking listing",
            description = "Only the supplied fields change (price, photos, publicationStatus, availabilityPeriods, ...).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Parking listing updated"),
            @ApiResponse(responseCode = "404", description = "Parking listing not found")
    })
    public ResponseEntity<ParkingListingResource> patch(@PathVariable Long id,
                                                        @RequestBody Map<String, Object> changes) {
        var existing = queryService.handle(new GetParkingListingByIdQuery(id));
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var current = ParkingListingResourceMapper.toResource(existing.get());
        var merged = new HashMap<String, Object>();
        merged.put("name", current.name());
        merged.put("address", current.address());
        merged.put("district", current.district());
        merged.put("hourlyRate", current.hourlyRate());
        merged.put("covered", current.covered());
        merged.put("availability", current.availability());
        merged.put("latitude", current.latitude());
        merged.put("longitude", current.longitude());
        merged.put("features", current.features());
        merged.put("photos", current.photos());
        merged.put("publicationStatus", current.publicationStatus());
        merged.put("availabilityPeriods", current.availabilityPeriods());
        merged.putAll(changes);
        UpdateParkingListingResource resource;
        try {
            resource = JsonText.convert(merged, UpdateParkingListingResource.class);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        var result = commandService.handle(UpdateParkingListingCommandMapper.toCommand(id, resource));
        return switch (result) {
            case Result.Success<ParkingListing, ParkingListingCommandFailure> s ->
                    ResponseEntity.ok(ParkingListingResourceMapper.toResource(s.value()));
            case Result.Failure<ParkingListing, ParkingListingCommandFailure> f ->
                    switch (f.error()) {
                        case ParkingListingCommandFailure.NotFound x -> ResponseEntity.notFound().build();
                        case ParkingListingCommandFailure.InvalidStatus x -> ResponseEntity.badRequest().build();
                    };
        };
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a parking listing")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Parking listing updated"),
            @ApiResponse(responseCode = "404", description = "Parking listing not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<ParkingListingResource> update(@PathVariable Long id,
                                                          @Valid @RequestBody UpdateParkingListingResource resource) {
        var command = UpdateParkingListingCommandMapper.toCommand(id, resource);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<ParkingListing, ParkingListingCommandFailure> s ->
                    ResponseEntity.ok(ParkingListingResourceMapper.toResource(s.value()));
            case Result.Failure<ParkingListing, ParkingListingCommandFailure> f ->
                    switch (f.error()) {
                        case ParkingListingCommandFailure.NotFound x -> ResponseEntity.notFound().build();
                        case ParkingListingCommandFailure.InvalidStatus x -> ResponseEntity.badRequest().build();
                    };
        };
    }
}
