package com.acme.parkshare.platform.booking.application.internal.commandservices;

import com.acme.parkshare.platform.booking.application.commandservices.ParkingListingCommandFailure;
import com.acme.parkshare.platform.booking.application.commandservices.ParkingListingCommandService;
import com.acme.parkshare.platform.booking.application.commands.CreateParkingListingCommand;
import com.acme.parkshare.platform.booking.application.commands.UpdateParkingListingCommand;
import com.acme.parkshare.platform.booking.domain.model.aggregates.ParkingListing;
import com.acme.parkshare.platform.booking.domain.repositories.ParkingListingRepository;
import com.acme.parkshare.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;

@Service
@Transactional
public class ParkingListingCommandServiceImpl implements ParkingListingCommandService {

    private final ParkingListingRepository repository;
    private final Clock clock;

    public ParkingListingCommandServiceImpl(ParkingListingRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    @Override
    public Result<ParkingListing, ParkingListingCommandFailure> handle(CreateParkingListingCommand command) {
        var listing = ParkingListing.create(
                command.ownerId(),
                command.name(),
                command.address(),
                command.district(),
                command.hourlyRate(),
                command.covered(),
                command.latitude(),
                command.longitude(),
                Instant.now(clock),
                command.features(),
                command.photos(),
                command.publicationStatus(),
                command.availabilityPeriods()
        );
        return Result.success(repository.save(listing));
    }

    @Override
    public Result<ParkingListing, ParkingListingCommandFailure> handle(UpdateParkingListingCommand command) {
        var existing = repository.findById(command.id());
        if (existing.isEmpty()) {
            return Result.failure(new ParkingListingCommandFailure.NotFound());
        }
        var current = existing.get();
        var updated = ParkingListing.rehydrate(
                current.getId(),
                current.getOwnerId(),
                command.name(),
                command.address(),
                command.district(),
                command.hourlyRate(),
                command.covered(),
                current.getRating(),
                current.getReviewCount(),
                command.availability(),
                command.latitude(),
                command.longitude(),
                current.getCreatedAt(),
                Instant.now(clock),
                command.features() != null ? command.features() : current.getFeatures(),
                command.photos() != null ? command.photos() : current.getPhotos(),
                command.publicationStatus() != null ? command.publicationStatus() : current.getPublicationStatus(),
                command.availabilityPeriods() != null ? command.availabilityPeriods() : current.getAvailabilityPeriods()
        );
        return Result.success(repository.save(updated));
    }
}
