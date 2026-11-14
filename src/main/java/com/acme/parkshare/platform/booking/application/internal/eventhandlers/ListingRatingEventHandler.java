package com.acme.parkshare.platform.booking.application.internal.eventhandlers;

import com.acme.parkshare.platform.booking.domain.repositories.ParkingListingRepository;
import com.acme.parkshare.platform.booking.domain.repositories.ReservationRepository;
import com.acme.parkshare.platform.reputationnotifications.domain.model.events.RatingSubmittedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;

/**
 * Keeps the listing's rating and review count in sync with the reviews it receives.
 * Only reviews written about the space's owner count towards the listing (a driver rating the
 * owner after a stay); an owner rating a driver says nothing about the space.
 */
@Component
public class ListingRatingEventHandler {

    private final ReservationRepository reservations;
    private final ParkingListingRepository listings;
    private final Clock clock;

    public ListingRatingEventHandler(ReservationRepository reservations, ParkingListingRepository listings,
                                     Clock clock) {
        this.reservations = reservations;
        this.listings = listings;
        this.clock = clock;
    }

    @EventListener
    @Transactional
    public void on(RatingSubmittedEvent event) {
        reservations.findById(event.bookingId())
                .filter(reservation -> event.revieweeId().equals(reservation.getOwnerId()))
                .flatMap(reservation -> listings.findById(reservation.getParkingListingId()))
                .ifPresent(listing -> listings.save(listing.withNewReview(event.score(), Instant.now(clock))));
    }
}
