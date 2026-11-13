package com.acme.parkshare.platform.reputationnotifications.application.internal.commandservices;

import com.acme.parkshare.platform.reputationnotifications.application.commands.CreateNotificationCommand;
import com.acme.parkshare.platform.reputationnotifications.application.commands.MarkNotificationAsReadCommand;
import com.acme.parkshare.platform.reputationnotifications.application.commandservices.NotificationCommandFailure;
import com.acme.parkshare.platform.reputationnotifications.application.commandservices.NotificationCommandService;
import com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates.Notification;
import com.acme.parkshare.platform.reputationnotifications.domain.repositories.NotificationRepository;
import com.acme.parkshare.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;

@Service
@Transactional
public class NotificationCommandServiceImpl implements NotificationCommandService {

    private final NotificationRepository repository;
    private final Clock clock;

    public NotificationCommandServiceImpl(NotificationRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    @Override
    public Result<Notification, NotificationCommandFailure> handle(CreateNotificationCommand command) {
        var notif = Notification.create(
                command.recipientId(),
                command.type(),
                command.title(),
                command.message(),
                command.relatedEntityType(),
                command.relatedEntityId(),
                Instant.now(clock)
        );
        return Result.success(repository.save(notif));
    }

    @Override
    public Result<Notification, NotificationCommandFailure> handle(MarkNotificationAsReadCommand command) {
        var existing = repository.findById(command.id());
        if (existing.isEmpty()) {
            return Result.failure(new NotificationCommandFailure.NotFound());
        }
        var updated = existing.get().markAsRead(Instant.now(clock));
        return Result.success(repository.save(updated));
    }
}
