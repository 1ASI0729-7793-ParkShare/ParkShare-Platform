package com.acme.parkshare.platform.reportanalytics.interfaces.rest.resources;

import com.acme.parkshare.platform.booking.interfaces.rest.resources.ReservationResource;

import java.math.BigDecimal;
import java.util.List;

public record OwnerDashboardResource(
        BigDecimal monthlyIncome,
        Integer monthlyChangePercent,
        int pendingRequests,
        int ongoingReservations,
        int publishedListings,
        int occupancyPercent,
        BigDecimal averageRating,
        List<DayIncomeResource> weeklyIncome,
        List<ReservationResource> recentActivity) {

    public record DayIncomeResource(String day, BigDecimal amount) {}
}
