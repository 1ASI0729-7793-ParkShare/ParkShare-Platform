package com.acme.parkshare.platform.reputationnotifications.infrastructure.persistence.jpa.adapters;

import com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates.Notification;
import com.acme.parkshare.platform.reputationnotifications.domain.repositories.NotificationRepository;
import com.acme.parkshare.platform.reputationnotifications.infrastructure.persistence.jpa.NotificationJpaRepository;
import com.acme.parkshare.platform.reputationnotifications.infrastructure.persistence.jpa.mappers.NotificationPersistenceMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class NotificationRepositoryAdapter implements NotificationRepository {

    private final NotificationJpaRepository repository;
    private final NotificationPersistenceMapper mapper;

    public NotificationRepositoryAdapter(NotificationJpaRepository repository, NotificationPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Notification save(Notification notification) {
        return mapper.toDomain(repository.save(mapper.toEntity(notification)));
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Notification> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Notification> findAllByRecipientId(Long recipientId) {
        return repository.findAllByRecipientId(recipientId).stream().map(mapper::toDomain).toList();
    }
}
