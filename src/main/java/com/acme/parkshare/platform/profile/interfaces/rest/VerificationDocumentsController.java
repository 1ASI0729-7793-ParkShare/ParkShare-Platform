package com.acme.parkshare.platform.profile.interfaces.rest;

import com.acme.parkshare.platform.profile.application.commandservices.VerificationDocumentCommandFailure;
import com.acme.parkshare.platform.profile.application.commandservices.VerificationDocumentCommandService;
import com.acme.parkshare.platform.profile.application.commands.CreateVerificationDocumentCommand;
import com.acme.parkshare.platform.profile.application.commands.UpdateVerificationDocumentStatusCommand;
import com.acme.parkshare.platform.profile.application.queries.GetAllVerificationDocumentsQuery;
import com.acme.parkshare.platform.profile.application.queries.GetVerificationDocumentByIdQuery;
import com.acme.parkshare.platform.profile.application.queryservices.VerificationDocumentQueryService;
import com.acme.parkshare.platform.profile.domain.model.aggregates.VerificationDocument;
import com.acme.parkshare.platform.profile.interfaces.rest.resources.CreateVerificationDocumentResource;
import com.acme.parkshare.platform.profile.interfaces.rest.resources.UpdateVerificationDocumentStatusResource;
import com.acme.parkshare.platform.profile.interfaces.rest.resources.VerificationDocumentResource;
import com.acme.parkshare.platform.profile.interfaces.rest.transform.VerificationDocumentResourceMapper;
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
@RequestMapping(value = "/api/v1/verification-documents", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Verification Documents", description = "Endpoints for verification document management")
public class VerificationDocumentsController {

    private final VerificationDocumentCommandService commandService;
    private final VerificationDocumentQueryService queryService;

    public VerificationDocumentsController(VerificationDocumentCommandService commandService,
                                           VerificationDocumentQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Create a verification document")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Document created"),
            @ApiResponse(responseCode = "400", description = "Invalid request")})
    public ResponseEntity<VerificationDocumentResource> create(@Valid @RequestBody CreateVerificationDocumentResource resource) {
        var command = new CreateVerificationDocumentCommand(resource.profileId(), resource.type(), resource.fileName());
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<VerificationDocument, VerificationDocumentCommandFailure> s ->
                    ResponseEntity.created(URI.create("/api/v1/verification-documents/" + s.value().getId()))
                            .body(VerificationDocumentResourceMapper.toResource(s.value()));
            case Result.Failure<VerificationDocument, VerificationDocumentCommandFailure> f ->
                    ResponseEntity.badRequest().build();
        };
    }

    @GetMapping
    @Operation(summary = "Get all verification documents")
    @ApiResponse(responseCode = "200", description = "Documents retrieved")
    public ResponseEntity<List<VerificationDocumentResource>> getAll() {
        return ResponseEntity.ok(queryService.handle(new GetAllVerificationDocumentsQuery())
                .stream().map(VerificationDocumentResourceMapper::toResource).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get verification document by ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Document found"),
            @ApiResponse(responseCode = "404", description = "Document not found")})
    public ResponseEntity<VerificationDocumentResource> getById(@PathVariable Long id) {
        return queryService.handle(new GetVerificationDocumentByIdQuery(id))
                .map(VerificationDocumentResourceMapper::toResource).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update verification document status")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Document updated"),
            @ApiResponse(responseCode = "404", description = "Document not found")})
    public ResponseEntity<VerificationDocumentResource> updateStatus(@PathVariable Long id,
            @Valid @RequestBody UpdateVerificationDocumentStatusResource resource) {
        var command = new UpdateVerificationDocumentStatusCommand(id, resource.status());
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<VerificationDocument, VerificationDocumentCommandFailure> s ->
                    ResponseEntity.ok(VerificationDocumentResourceMapper.toResource(s.value()));
            case Result.Failure<VerificationDocument, VerificationDocumentCommandFailure> f ->
                    switch (f.error()) {
                        case VerificationDocumentCommandFailure.DocumentNotFound x -> ResponseEntity.notFound().build();
                        case VerificationDocumentCommandFailure.InvalidStatus x -> ResponseEntity.badRequest().build();
                        case VerificationDocumentCommandFailure.ProfileNotFound x -> ResponseEntity.badRequest().build();
                    };
        };
    }
}
