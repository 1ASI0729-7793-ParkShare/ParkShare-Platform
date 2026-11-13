package com.acme.parkshare.platform.reputationnotifications.infrastructure.persistence.jpa;

import com.acme.parkshare.platform.reputationnotifications.infrastructure.persistence.jpa.entities.NotificationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationJpaRepository extends JpaRepository<NotificationJpaEntity, Long> {
    List<NotificationJpaEntity> findAllByRecipientId(Long recipientId);
}
