package com.acme.parkshare.platform.reportanalytics.domain.services;

import com.acme.parkshare.platform.booking.domain.model.aggregates.ParkingListing;
import com.acme.parkshare.platform.booking.domain.model.aggregates.Reservation;
import com.acme.parkshare.platform.reportanalytics.domain.model.valueobjects.DayIncome;
import com.acme.parkshare.platform.reportanalytics.domain.model.valueobjects.OwnerDashboard;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/**
 * Pure calculation of the owner's dashboard. Rules:
 * <ul>
 *   <li>A reservation earns money only when it is completed; the amount is its total cost.</li>
 *   <li>It counts on the day it ended (else started, else was planned for).</li>
 *   <li>Occupancy is the share of the owner's listings with a reservation in use right now.</li>
 *   <li>The average rating is the mean of the listings' ratings; listings with no reviews yet are skipped.</li>
 * </ul>
 */
public final class OwnerDashboardCalculator {

    private static final String[] DAYS = {"mon", "tue", "wed", "thu", "fri", "sat", "sun"};
    public static final int RECENT_ACTIVITY_LIMIT = 5;

    private OwnerDashboardCalculator() {}

    public static OwnerDashboard calculate(List<Reservation> reservations, List<ParkingListing> listings,
                                           Instant now, ZoneId zone) {
        var today = LocalDate.ofInstant(now, zone);
        var month = YearMonth.from(today);
        var previousMonth = month.minusMonths(1);

        var monthlyIncome = incomeOf(reservations, zone, month);
        var previousIncome = incomeOf(reservations, zone, previousMonth);
        Integer change = previousIncome.signum() > 0
                ? monthlyIncome.subtract(previousIncome).multiply(BigDecimal.valueOf(100))
                        .divide(previousIncome, 0, RoundingMode.HALF_UP).intValue()
                : null;

        var weekStart = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        var perDay = new BigDecimal[7];
        java.util.Arrays.fill(perDay, BigDecimal.ZERO);
        for (var reservation : reservations) {
            var date = LocalDate.ofInstant(occurredAt(reservation), zone);
            if (!date.isBefore(weekStart) && date.isBefore(weekStart.plusDays(7))) {
                int index = date.getDayOfWeek().getValue() - 1;
                perDay[index] = perDay[index].add(earned(reservation));
            }
        }
        var weekly = new ArrayList<DayIncome>();
        for (int i = 0; i < 7; i++) {
            weekly.add(new DayIncome(DAYS[i], perDay[i].setScale(2, RoundingMode.HALF_UP)));
        }

        var ongoing = reservations.stream().filter(r -> "in-use".equals(r.getStatus())).toList();
        var occupied = new HashSet<Long>();
        ongoing.forEach(r -> occupied.add(r.getParkingListingId()));
        int occupancy = listings.isEmpty() ? 0
                : (int) Math.round(occupied.size() * 100.0 / listings.size());

        var rated = listings.stream().filter(l -> l.getReviewCount() > 0).toList();
        BigDecimal average = rated.isEmpty() ? null
                : rated.stream().map(ParkingListing::getRating).reduce(BigDecimal.ZERO, BigDecimal::add)
                        .divide(BigDecimal.valueOf(rated.size()), 1, RoundingMode.HALF_UP);

        var recent = reservations.stream()
                .sorted(Comparator.comparing(OwnerDashboardCalculator::occurredAt).reversed())
                .limit(RECENT_ACTIVITY_LIMIT)
                .toList();

        return new OwnerDashboard(
                monthlyIncome,
                change,
                (int) reservations.stream().filter(r -> "requested".equals(r.getStatus())).count(),
                ongoing.size(),
                listings.size(),
                occupancy,
                average,
                weekly,
                recent);
    }

    private static BigDecimal incomeOf(List<Reservation> reservations, ZoneId zone, YearMonth month) {
        return reservations.stream()
                .filter(r -> YearMonth.from(LocalDate.ofInstant(occurredAt(r), zone)).equals(month))
                .map(OwnerDashboardCalculator::earned)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private static BigDecimal earned(Reservation reservation) {
        return "completed".equals(reservation.getStatus()) && reservation.getTotalCost() != null
                ? reservation.getTotalCost() : BigDecimal.ZERO;
    }

    static Instant occurredAt(Reservation reservation) {
        if (reservation.getEndedAt() != null) return reservation.getEndedAt();
        if (reservation.getStartedAt() != null) return reservation.getStartedAt();
        return reservation.getPlannedEntry();
    }
}
