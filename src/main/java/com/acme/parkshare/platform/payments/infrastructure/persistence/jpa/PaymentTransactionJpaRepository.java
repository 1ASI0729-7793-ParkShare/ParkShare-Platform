package com.acme.parkshare.platform.payments.infrastructure.persistence.jpa;

import com.acme.parkshare.platform.payments.infrastructure.persistence.jpa.entities.PaymentTransactionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentTransactionJpaRepository extends JpaRepository<PaymentTransactionJpaEntity, Long> {
    List<PaymentTransactionJpaEntity> findAllByOwnerIdOrderByDateDesc(Long ownerId);
    boolean existsByReservationId(Long reservationId);
}
