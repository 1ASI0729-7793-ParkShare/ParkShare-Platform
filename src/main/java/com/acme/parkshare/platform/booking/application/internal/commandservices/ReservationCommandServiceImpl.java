package com.acme.parkshare.platform.booking.application.internal.commandservices;

import com.acme.parkshare.platform.booking.application.commandservices.ReservationCommandFailure;
import com.acme.parkshare.platform.booking.application.commandservices.ReservationCommandService;
import com.acme.parkshare.platform.booking.application.commands.CreateReservationCommand;
import com.acme.parkshare.platform.booking.application.commands.UpdateReservationStatusCommand;
import com.acme.parkshare.platform.booking.domain.model.aggregates.Reservation;
import com.acme.parkshare.platform.booking.domain.repositories.ReservationRepository;
import com.acme.parkshare.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;

@Service
@Transactional
public class ReservationCommandServiceImpl implements ReservationCommandService {

    private final ReservationRepository repository;
    private final Clock clock;

    public ReservationCommandServiceImpl(ReservationRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    @Override
    public Result<Reservation, ReservationCommandFailure> handle(CreateReservationCommand command) {
        var reservation = Reservation.create(
                command.parkingListingId(),
                command.ownerId(),
                command.driverId(),
                command.driverName(),
                command.vehiclePlate(),
                command.vehicleModel(),
                command.spaceName(),
                command.spaceAddress(),
                command.hourlyRate(),
                command.plannedEntry(),
                command.plannedHours(),
                Instant.now(clock)
        );
        return Result.success(repository.save(reservation));
    }

    @Override
    public Result<Reservation, ReservationCommandFailure> handle(UpdateReservationStatusCommand command) {
        var existing = repository.findById(command.id());
        if (existing.isEmpty()) {
            return Result.failure(new ReservationCommandFailure.NotFound());
        }
        var current = existing.get();
        Instant now = Instant.now(clock);
        Reservation updated = switch (command.newStatus()) {
            case "confirmed" -> current.confirm(now);
            case "rejected" -> current.reject(now);
            case "in-use" -> current.start(now);
            case "completed" -> current.finish(now);
            default -> null;
        };
        if (updated == null) {
            return Result.failure(new ReservationCommandFailure.InvalidStatus(
                    "Unknown status transition: " + command.newStatus()));
        }
        return Result.success(repository.save(updated));
    }
}
