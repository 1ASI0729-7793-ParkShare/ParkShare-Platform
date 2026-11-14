package com.acme.parkshare.platform.reportanalytics.application.internal.queryservices;

import com.acme.parkshare.platform.booking.domain.repositories.ParkingListingRepository;
import com.acme.parkshare.platform.booking.domain.repositories.ReservationRepository;
import com.acme.parkshare.platform.reportanalytics.application.queries.GetOwnerDashboardQuery;
import com.acme.parkshare.platform.reportanalytics.application.queryservices.OwnerDashboardQueryService;
import com.acme.parkshare.platform.reportanalytics.domain.model.valueobjects.OwnerDashboard;
import com.acme.parkshare.platform.reportanalytics.domain.services.OwnerDashboardCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Service
@Transactional(readOnly = true)
public class OwnerDashboardQueryServiceImpl implements OwnerDashboardQueryService {

    private final ReservationRepository reservations;
    private final ParkingListingRepository listings;
    private final Clock clock;
    private final ZoneId zone;

    public OwnerDashboardQueryServiceImpl(ReservationRepository reservations, ParkingListingRepository listings,
                                          Clock clock, @Value("${app.timezone:America/Lima}") String zone) {
        this.reservations = reservations;
        this.listings = listings;
        this.clock = clock;
        this.zone = ZoneId.of(zone);
    }

    @Override
    public OwnerDashboard handle(GetOwnerDashboardQuery query) {
        return OwnerDashboardCalculator.calculate(
                reservations.findAllByOwnerId(query.ownerId()),
                listings.findAllByOwnerId(query.ownerId()),
                Instant.now(clock),
                zone);
    }
}
