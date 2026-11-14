package com.acme.parkshare.platform.emergencyalerts.application.internal.commandservices;

import com.acme.parkshare.platform.emergencyalerts.application.commands.CreateEmergencyAlertCommand;
import com.acme.parkshare.platform.emergencyalerts.application.commands.ResolveEmergencyAlertCommand;
import com.acme.parkshare.platform.emergencyalerts.application.commandservices.EmergencyAlertCommandFailure;
import com.acme.parkshare.platform.emergencyalerts.application.commandservices.EmergencyAlertCommandService;
import com.acme.parkshare.platform.emergencyalerts.domain.model.aggregates.EmergencyAlert;
import com.acme.parkshare.platform.emergencyalerts.domain.repositories.EmergencyAlertRepository;
import com.acme.parkshare.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;

@Service
@Transactional
public class EmergencyAlertCommandServiceImpl implements EmergencyAlertCommandService {

    private final EmergencyAlertRepository repository;
    private final Clock clock;

    public EmergencyAlertCommandServiceImpl(EmergencyAlertRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    @Override
    public Result<EmergencyAlert, EmergencyAlertCommandFailure> handle(CreateEmergencyAlertCommand command) {
        var alert = EmergencyAlert.create(
                command.ownerId(),
                command.parkingListingId(),
                command.spaceName(),
                command.kind(),
                command.notes(),
                command.latitude(),
                command.longitude(),
                command.locationSource(),
                Instant.now(clock)
        );
        return Result.success(repository.save(alert));
    }

    @Override
    public Result<EmergencyAlert, EmergencyAlertCommandFailure> handle(ResolveEmergencyAlertCommand command) {
        var existing = repository.findById(command.id());
        if (existing.isEmpty()) {
            return Result.failure(new EmergencyAlertCommandFailure.NotFound());
        }
        var updated = existing.get().resolve(Instant.now(clock));
        return Result.success(repository.save(updated));
    }
}
