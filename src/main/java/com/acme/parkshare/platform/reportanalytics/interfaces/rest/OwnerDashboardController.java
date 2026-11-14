package com.acme.parkshare.platform.reportanalytics.interfaces.rest;

import com.acme.parkshare.platform.booking.interfaces.rest.transform.ReservationResourceMapper;
import com.acme.parkshare.platform.reportanalytics.application.queries.GetOwnerDashboardQuery;
import com.acme.parkshare.platform.reportanalytics.application.queryservices.OwnerDashboardQueryService;
import com.acme.parkshare.platform.reportanalytics.interfaces.rest.resources.OwnerDashboardResource;
import com.acme.parkshare.platform.reportanalytics.interfaces.rest.resources.OwnerDashboardResource.DayIncomeResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/owner-dashboard", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Report & Analytics", description = "Dashboard figures of a parking owner")
public class OwnerDashboardController {

    private final OwnerDashboardQueryService queryService;

    public OwnerDashboardController(OwnerDashboardQueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping
    @Operation(summary = "Get the dashboard of an owner (?ownerId=)",
            description = "Monthly income and change, pending/ongoing reservations, occupancy, average rating, "
                    + "income of the current week and the latest activity. Calculated by the server.")
    public ResponseEntity<OwnerDashboardResource> get(@RequestParam Long ownerId) {
        var d = queryService.handle(new GetOwnerDashboardQuery(ownerId));
        return ResponseEntity.ok(new OwnerDashboardResource(
                d.monthlyIncome(), d.monthlyChangePercent(), d.pendingRequests(), d.ongoingReservations(),
                d.publishedListings(), d.occupancyPercent(), d.averageRating(),
                d.weeklyIncome().stream().map(w -> new DayIncomeResource(w.day(), w.amount())).toList(),
                d.recentActivity().stream().map(ReservationResourceMapper::toResource).toList()));
    }
}
