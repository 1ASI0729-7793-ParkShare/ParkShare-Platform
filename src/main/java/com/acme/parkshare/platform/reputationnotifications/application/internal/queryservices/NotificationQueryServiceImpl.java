package com.acme.parkshare.platform.reputationnotifications.application.internal.queryservices;

import com.acme.parkshare.platform.reputationnotifications.application.queries.GetAllNotificationsQuery;
import com.acme.parkshare.platform.reputationnotifications.application.queries.GetNotificationByIdQuery;
import com.acme.parkshare.platform.reputationnotifications.application.queryservices.NotificationQueryService;
import com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates.Notification;
import com.acme.parkshare.platform.reputationnotifications.domain.repositories.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class NotificationQueryServiceImpl implements NotificationQueryService {

    private final NotificationRepository repository;

    public NotificationQueryServiceImpl(NotificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Notification> handle(GetAllNotificationsQuery query) {
        return repository.findAll();
    }

    @Override
    public Optional<Notification> handle(GetNotificationByIdQuery query) {
        return repository.findById(query.id());
    }
}
