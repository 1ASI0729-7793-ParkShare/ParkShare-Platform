package com.acme.parkshare.platform.parkingspacemanagement.application.internal.commandservices;

import com.acme.parkshare.platform.parkingspacemanagement.application.commands.CreateParkingSpaceCommand;
import com.acme.parkshare.platform.parkingspacemanagement.application.commands.DeleteParkingSpaceCommand;
import com.acme.parkshare.platform.parkingspacemanagement.application.commands.UpdateParkingSpaceCommand;
import com.acme.parkshare.platform.parkingspacemanagement.application.commandservices.ParkingSpaceCommandFailure;
import com.acme.parkshare.platform.parkingspacemanagement.application.commandservices.ParkingSpaceCommandService;
import com.acme.parkshare.platform.parkingspacemanagement.domain.model.aggregates.ParkingSpace;
import com.acme.parkshare.platform.parkingspacemanagement.domain.repositories.ParkingSpaceRepository;
import com.acme.parkshare.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;

@Service
@Transactional
public class ParkingSpaceCommandServiceImpl implements ParkingSpaceCommandService {

    private final ParkingSpaceRepository repository;
    private final Clock clock;

    public ParkingSpaceCommandServiceImpl(ParkingSpaceRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    @Override
    public Result<ParkingSpace, ParkingSpaceCommandFailure> handle(CreateParkingSpaceCommand command) {
        var space = ParkingSpace.create(
                command.ownerId(),
                command.address(),
                command.name(),
                command.hourlyRate(),
                command.features(),
                command.availabilityPeriods(),
                Instant.now(clock)
        );
        return Result.success(repository.save(space));
    }

    @Override
    public Result<ParkingSpace, ParkingSpaceCommandFailure> handle(UpdateParkingSpaceCommand command) {
        var existing = repository.findById(command.id());
        if (existing.isEmpty()) {
            return Result.failure(new ParkingSpaceCommandFailure.NotFound());
        }
        var cur = existing.get();
        var updated = ParkingSpace.rehydrate(
                cur.getId(),
                cur.getOwnerId(),
                command.address(),
                command.name(),
                command.hourlyRate(),
                command.features(),
                command.publicationStatus() != null ? command.publicationStatus() : cur.getPublicationStatus(),
                cur.getAccumulatedIncome(),
                command.availabilityPeriods(),
                cur.getCreatedAt(),
                Instant.now(clock)
        );
        return Result.success(repository.save(updated));
    }

    @Override
    public Result<Void, ParkingSpaceCommandFailure> handle(DeleteParkingSpaceCommand command) {
        if (repository.findById(command.id()).isEmpty()) {
            return Result.failure(new ParkingSpaceCommandFailure.NotFound());
        }
        repository.deleteById(command.id());
        return Result.success(null);
    }
}
