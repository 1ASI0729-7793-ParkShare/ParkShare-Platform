package com.acme.parkshare.platform.incidentreporting.application.internal.commandservices;

import com.acme.parkshare.platform.incidentreporting.application.commandservices.IncidentCommandFailure;
import com.acme.parkshare.platform.incidentreporting.application.commandservices.IncidentCommandService;
import com.acme.parkshare.platform.incidentreporting.application.commands.CreateIncidentCommand;
import com.acme.parkshare.platform.incidentreporting.application.commands.UpdateIncidentStatusCommand;
import com.acme.parkshare.platform.incidentreporting.domain.model.aggregates.Incident;
import com.acme.parkshare.platform.incidentreporting.domain.repositories.IncidentRepository;
import com.acme.parkshare.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;

@Service
@Transactional
public class IncidentCommandServiceImpl implements IncidentCommandService {

    private final IncidentRepository repository;
    private final Clock clock;

    public IncidentCommandServiceImpl(IncidentRepository repository, Clock clock) {
        this.repository = repository; this.clock = clock;
    }

    @Override
    public Result<Incident, IncidentCommandFailure> handle(CreateIncidentCommand command) {
        var incident = Incident.create(command.reporterId(), command.reporterName(),
                command.parkingListingId(), command.ownerId(), command.spaceName(),
                command.spaceAddress(), command.incidentTypes(), command.description(),
                command.photos(), Instant.now(clock));
        return Result.success(repository.save(incident));
    }

    @Override
    public Result<Incident, IncidentCommandFailure> handle(UpdateIncidentStatusCommand command) {
        var existing = repository.findById(command.id());
        if (existing.isEmpty()) return Result.failure(new IncidentCommandFailure.NotFound());
        var now = Instant.now(clock);
        Incident updated = switch (command.newStatus()) {
            case "in-review" -> existing.get().startReview(now);
            case "resolved" -> existing.get().resolve(now);
            default -> null;
        };
        if (updated == null) return Result.failure(new IncidentCommandFailure.InvalidStatus("Unknown status: " + command.newStatus()));
        return Result.success(repository.save(updated));
    }
}
