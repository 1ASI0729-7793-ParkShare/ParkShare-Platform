package com.acme.parkshare.platform.reportanalytics.domain.model.valueobjects;

import com.acme.parkshare.platform.booking.domain.model.aggregates.Reservation;

import java.math.BigDecimal;
import java.util.List;

/** Read model with the numbers shown on the owner's dashboard. */
public record OwnerDashboard(
        BigDecimal monthlyIncome,
        Integer monthlyChangePercent,
        int pendingRequests,
        int ongoingReservations,
        int publishedListings,
        int occupancyPercent,
        BigDecimal averageRating,
        List<DayIncome> weeklyIncome,
        List<Reservation> recentActivity) {}
