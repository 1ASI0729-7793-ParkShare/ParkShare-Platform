package com.acme.parkshare.platform.reputationnotifications.interfaces.rest;

import com.acme.parkshare.platform.reputationnotifications.application.queries.GetAllUserReputationsQuery;
import com.acme.parkshare.platform.reputationnotifications.application.queries.GetUserReputationByUserIdQuery;
import com.acme.parkshare.platform.reputationnotifications.application.queryservices.UserReputationQueryService;
import com.acme.parkshare.platform.reputationnotifications.interfaces.rest.resources.UserReputationResource;
import com.acme.parkshare.platform.reputationnotifications.interfaces.rest.transform.UserReputationResourceMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/user-reputations", produces = APPLICATION_JSON_VALUE)
@Tag(name = "User Reputations", description = "Endpoints for user reputation and score")
public class UserReputationsController {

    private final UserReputationQueryService queryService;

    public UserReputationsController(UserReputationQueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping
    @Operation(summary = "Get all user reputations")
    @ApiResponse(responseCode = "200", description = "User reputations retrieved")
    public ResponseEntity<List<UserReputationResource>> getAll() {
        var list = queryService.handle(new GetAllUserReputationsQuery())
                .stream().map(UserReputationResourceMapper::toResource).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user reputation by user ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User reputation found"),
            @ApiResponse(responseCode = "404", description = "User reputation not found")
    })
    public ResponseEntity<UserReputationResource> getByUserId(@PathVariable Long userId) {
        return queryService.handle(new GetUserReputationByUserIdQuery(userId))
                .map(UserReputationResourceMapper::toResource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
