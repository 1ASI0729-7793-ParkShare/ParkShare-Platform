package com.acme.parkshare.platform.reputationnotifications.application.queryservices;

import com.acme.parkshare.platform.reputationnotifications.application.queries.GetAllNotificationsQuery;
import com.acme.parkshare.platform.reputationnotifications.application.queries.GetNotificationByIdQuery;
import com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationQueryService {
    List<Notification> handle(GetAllNotificationsQuery query);
    Optional<Notification> handle(GetNotificationByIdQuery query);
}
