package com.acme.parkshare.platform.shared.infrastructure.seed;

import com.acme.parkshare.platform.booking.application.commandservices.ParkingListingCommandService;
import com.acme.parkshare.platform.booking.application.commandservices.ReservationCommandService;
import com.acme.parkshare.platform.booking.application.commands.CreateParkingListingCommand;
import com.acme.parkshare.platform.booking.application.commands.CreateReservationCommand;
import com.acme.parkshare.platform.booking.application.commands.UpdateReservationStatusCommand;
import com.acme.parkshare.platform.booking.domain.model.aggregates.ParkingListing;
import com.acme.parkshare.platform.booking.domain.model.aggregates.Reservation;
import com.acme.parkshare.platform.booking.domain.repositories.ParkingListingRepository;
import com.acme.parkshare.platform.profile.application.commands.CreateProfileCommand;
import com.acme.parkshare.platform.profile.application.commands.CreateVerificationDocumentCommand;
import com.acme.parkshare.platform.profile.application.commands.UpdateVerificationDocumentStatusCommand;
import com.acme.parkshare.platform.profile.application.commandservices.ProfileCommandService;
import com.acme.parkshare.platform.profile.application.commandservices.VerificationDocumentCommandService;
import com.acme.parkshare.platform.profile.domain.repositories.ProfileRepository;
import com.acme.parkshare.platform.reputationnotifications.application.commandservices.RatingCommandService;
import com.acme.parkshare.platform.reputationnotifications.application.commands.CreateRatingCommand;
import com.acme.parkshare.platform.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Loads demo data on an empty database (replaces the old json-server db.json).
 * Reservations are driven through the command service so that completed ones
 * generate their payment transactions exactly as in production.
 * Owner 1 and driver 2 match the ids the frontend uses until login is wired.
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true")
public class DemoDataSeeder {

    private final ParkingListingRepository listingRepository;
    private final ParkingListingCommandService listingService;
    private final ReservationCommandService reservationService;
    private final RatingCommandService ratingService;
    private final ProfileRepository profileRepository;
    private final ProfileCommandService profileService;
    private final VerificationDocumentCommandService documentService;

    public DemoDataSeeder(ParkingListingRepository listingRepository,
                          ParkingListingCommandService listingService,
                          ReservationCommandService reservationService,
                          RatingCommandService ratingService,
                          ProfileRepository profileRepository,
                          ProfileCommandService profileService,
                          VerificationDocumentCommandService documentService) {
        this.profileRepository = profileRepository;
        this.profileService = profileService;
        this.documentService = documentService;
        this.listingRepository = listingRepository;
        this.listingService = listingService;
        this.reservationService = reservationService;
        this.ratingService = ratingService;
    }

    @EventListener
    public void on(ApplicationReadyEvent event) {
        seedProfiles();
        if (!listingRepository.findAll().isEmpty()) {
            return;
        }
        log.info("Seeding demo data");
        var weekdays = "[" + String.join(",", List.of("monday", "tuesday", "wednesday", "thursday", "friday").stream()
                .map(day -> "{\"dayOfWeek\":\"" + day + "\",\"startTime\":\"08:00\",\"endTime\":\"20:00\"}").toList()) + "]";

        var larco = listing(1L, "Estacionamiento techado Larco", "Av. Larco 841", "6.00", -12.1219, -77.0297, weekdays);
        var benavides = listing(1L, "Cochera Residencial Benavides", "Av. Alfredo Benavides 1220", "5.50", -12.1288, -77.0206, weekdays);
        listing(1L, "Estacionamiento Seguro Diagonal", "Calle Diagonal 340", "7.00", -12.1231, -77.0312, weekdays);
        listing(2L, "Cochera Privada Cantuarias", "Calle Cantuarias 160", "5.00", -12.1256, -77.0273, weekdays);

        var now = Instant.now();
        var completedA = reservation(larco, 2L, "Mateo Silva", "F4W-210", "Hyundai Accent", now.minus(3, ChronoUnit.DAYS), 2);
        advance(completedA, "confirmed", "in-use", "completed");
        var completedB = reservation(benavides, 3L, "Lucía Paredes", "XYZ-789", "Kia Rio", now.minus(2, ChronoUnit.DAYS), 4);
        advance(completedB, "confirmed", "in-use", "completed");
        // Reviews of the owner: they feed the listings' rating/reviewCount and the owner's reputation.
        rate(completedA, 5, "Excelente servicio, muy fácil de encontrar.");
        rate(completedB, 4, "Buena cochera, el acceso es estrecho.");
        var inUse = reservation(larco, 2L, "Mateo Silva", "F4W-210", "Hyundai Accent", now.minus(1, ChronoUnit.HOURS), 2);
        advance(inUse, "confirmed", "in-use");
        var confirmed = reservation(benavides, 2L, "Mateo Silva", "F4W-210", "Hyundai Accent", now.plus(1, ChronoUnit.DAYS), 2);
        advance(confirmed, "confirmed");
        reservation(larco, 3L, "Lucía Paredes", "XYZ-789", "Kia Rio", now.plus(2, ChronoUnit.DAYS), 3);
    }

    /** Profile 1 = owner (Carlos), profile 2 = driver (Mateo): same ids the frontend uses. */
    private void seedProfiles() {
        if (!profileRepository.findAll().isEmpty()) {
            return;
        }
        profileService.handle(new CreateProfileCommand("Carlos Mendoza", "ABC-123", "Toyota Yaris", "sedan"));
        var driver = profileService.handle(new CreateProfileCommand("Mateo Silva", "F4W-210", "Hyundai Accent", "sedan"));
        var driverId = ((Result.Success<com.acme.parkshare.platform.profile.domain.model.aggregates.Profile, ?>) driver)
                .value().getId();
        var nationalId = documentService.handle(new CreateVerificationDocumentCommand(driverId, "national-id", "dni.pdf"));
        var license = documentService.handle(new CreateVerificationDocumentCommand(driverId, "driver-license", "licencia.pdf"));
        documentService.handle(new CreateVerificationDocumentCommand(driverId, "vehicle-registration", null));
        verify(nationalId);
        verify(license);
    }

    private void verify(Result<com.acme.parkshare.platform.profile.domain.model.aggregates.VerificationDocument, ?> created) {
        var id = ((Result.Success<com.acme.parkshare.platform.profile.domain.model.aggregates.VerificationDocument, ?>) created)
                .value().getId();
        documentService.handle(new UpdateVerificationDocumentStatusCommand(id, "verified"));
    }

    private ParkingListing listing(Long ownerId, String name, String address, String rate,
                                   double latitude, double longitude, String availabilityPeriods) {
        var result = listingService.handle(new CreateParkingListingCommand(ownerId, name, address, "Miraflores",
                new BigDecimal(rate), true, latitude, longitude, "[\"covered\"]", "[]", "published",
                availabilityPeriods));
        return ((Result.Success<ParkingListing, ?>) result).value();
    }

    private Reservation reservation(ParkingListing listing, Long driverId, String driverName, String plate,
                                    String model, Instant entry, int hours) {
        var result = reservationService.handle(new CreateReservationCommand(listing.getId(), listing.getOwnerId(),
                driverId, driverName, plate, model, listing.getName(), listing.getAddress(),
                listing.getHourlyRate(), entry, hours));
        return ((Result.Success<Reservation, ?>) result).value();
    }

    private void rate(Reservation reservation, int score, String comment) {
        ratingService.handle(new CreateRatingCommand(reservation.getId(), reservation.getDriverId(),
                reservation.getOwnerId(), score, comment));
    }

    private void advance(Reservation reservation, String... statuses) {
        for (var status : statuses) {
            reservationService.handle(new UpdateReservationStatusCommand(reservation.getId(), status));
        }
    }
}
