package com.acme.parkshare.platform.reputationnotifications.interfaces.rest;

import com.acme.parkshare.platform.reputationnotifications.application.commands.CreateRatingCommand;
import com.acme.parkshare.platform.reputationnotifications.application.commandservices.RatingCommandFailure;
import com.acme.parkshare.platform.reputationnotifications.application.commandservices.RatingCommandService;
import com.acme.parkshare.platform.reputationnotifications.application.queries.GetAllRatingsQuery;
import com.acme.parkshare.platform.reputationnotifications.application.queries.GetRatingByIdQuery;
import com.acme.parkshare.platform.reputationnotifications.application.queryservices.RatingQueryService;
import com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates.Rating;
import com.acme.parkshare.platform.reputationnotifications.interfaces.rest.resources.CreateRatingResource;
import com.acme.parkshare.platform.reputationnotifications.interfaces.rest.resources.RatingResource;
import com.acme.parkshare.platform.reputationnotifications.interfaces.rest.transform.RatingResourceMapper;
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
@RequestMapping(value = "/api/v1/ratings", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Ratings", description = "Endpoints for user ratings and reviews")
public class RatingsController {

    private final RatingCommandService commandService;
    private final RatingQueryService queryService;

    public RatingsController(RatingCommandService commandService, RatingQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Submit a rating")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Rating submitted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid rating payload")
    })
    public ResponseEntity<RatingResource> create(@Valid @RequestBody CreateRatingResource resource) {
        var command = new CreateRatingCommand(
                resource.bookingId(),
                resource.reviewerId(),
                resource.revieweeId(),
                resource.score(),
                resource.comment()
        );
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<Rating, RatingCommandFailure> s ->
                    ResponseEntity.created(URI.create("/api/v1/ratings/" + s.value().getId()))
                            .body(RatingResourceMapper.toResource(s.value()));
            case Result.Failure<Rating, RatingCommandFailure> f ->
                    ResponseEntity.badRequest().build();
        };
    }

    @GetMapping
    @Operation(summary = "Get all ratings")
    @ApiResponse(responseCode = "200", description = "Ratings retrieved")
    public ResponseEntity<List<RatingResource>> getAll() {
        var list = queryService.handle(new GetAllRatingsQuery())
                .stream().map(RatingResourceMapper::toResource).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get rating by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rating found"),
            @ApiResponse(responseCode = "404", description = "Rating not found")
    })
    public ResponseEntity<RatingResource> getById(@PathVariable Long id) {
        return queryService.handle(new GetRatingByIdQuery(id))
                .map(RatingResourceMapper::toResource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
