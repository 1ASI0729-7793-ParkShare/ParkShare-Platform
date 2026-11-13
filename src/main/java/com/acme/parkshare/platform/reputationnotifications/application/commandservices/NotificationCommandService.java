package com.acme.parkshare.platform.reputationnotifications.application.commandservices;

import com.acme.parkshare.platform.reputationnotifications.application.commands.CreateNotificationCommand;
import com.acme.parkshare.platform.reputationnotifications.application.commands.MarkNotificationAsReadCommand;
import com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates.Notification;
import com.acme.parkshare.platform.shared.application.result.Result;

public interface NotificationCommandService {
    Result<Notification, NotificationCommandFailure> handle(CreateNotificationCommand command);
    Result<Notification, NotificationCommandFailure> handle(MarkNotificationAsReadCommand command);
}
