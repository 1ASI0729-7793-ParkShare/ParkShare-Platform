package com.acme.parkshare.platform.payments.interfaces.rest;

import com.acme.parkshare.platform.payments.application.commandservices.PaymentsCommandFailure;
import com.acme.parkshare.platform.payments.application.commandservices.PaymentsCommandService;
import com.acme.parkshare.platform.payments.application.commands.RequestOwnerTransferCommand;
import com.acme.parkshare.platform.payments.application.queries.GetOwnerPayoutByOwnerIdQuery;
import com.acme.parkshare.platform.payments.application.queryservices.PaymentsQueryService;
import com.acme.parkshare.platform.payments.domain.model.aggregates.OwnerPayout;
import com.acme.parkshare.platform.payments.interfaces.rest.resources.OwnerPayoutResource;
import com.acme.parkshare.platform.shared.application.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/owner-payouts", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Owner Payouts", description = "Earnings summary and bank transfers of parking owners")
public class OwnerPayoutsController {

    private final PaymentsQueryService queryService;
    private final PaymentsCommandService commandService;

    public OwnerPayoutsController(PaymentsQueryService queryService, PaymentsCommandService commandService) {
        this.queryService = queryService;
        this.commandService = commandService;
    }

    @GetMapping
    @Operation(summary = "Get the earnings summary of an owner (?ownerId=)")
    public ResponseEntity<List<OwnerPayoutResource>> getByOwner(@RequestParam Long ownerId) {
        return ResponseEntity.ok(List.of(toResource(queryService.handle(new GetOwnerPayoutByOwnerIdQuery(ownerId)))));
    }

    @PostMapping("/{id}/transfer")
    @Operation(summary = "Transfer the available balance to the owner's bank account")
    public ResponseEntity<OwnerPayoutResource> transfer(@PathVariable Long id) {
        var result = commandService.handle(new RequestOwnerTransferCommand(id));
        return switch (result) {
            case Result.Success<OwnerPayout, PaymentsCommandFailure> s -> ResponseEntity.ok(toResource(s.value()));
            case Result.Failure<OwnerPayout, PaymentsCommandFailure> f ->
                    switch (f.error()) {
                        case PaymentsCommandFailure.NotFound x -> ResponseEntity.notFound().build();
                        case PaymentsCommandFailure.NothingToTransfer x -> ResponseEntity.unprocessableEntity().build();
                    };
        };
    }

    private static OwnerPayoutResource toResource(OwnerPayout p) {
        return new OwnerPayoutResource(p.getId(), p.getOwnerId(), p.getAvailableBalance(), p.getCompletedAmount(),
                p.getInTransitAmount(), p.getLinkedAccount(), p.getCommissionRate());
    }
}
