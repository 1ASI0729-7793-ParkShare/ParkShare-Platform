package com.acme.parkshare.platform.reputationnotifications.application.commandservices;

public sealed interface NotificationCommandFailure permits
        NotificationCommandFailure.NotFound {
    record NotFound() implements NotificationCommandFailure {}
}
