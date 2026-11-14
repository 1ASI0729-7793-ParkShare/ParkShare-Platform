package com.acme.parkshare.platform.payments.infrastructure.persistence.jpa.adapters;

import com.acme.parkshare.platform.payments.domain.model.aggregates.PaymentTransaction;
import com.acme.parkshare.platform.payments.domain.repositories.PaymentTransactionRepository;
import com.acme.parkshare.platform.payments.infrastructure.persistence.jpa.PaymentTransactionJpaRepository;
import com.acme.parkshare.platform.payments.infrastructure.persistence.jpa.entities.PaymentTransactionJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentTransactionRepositoryAdapter implements PaymentTransactionRepository {

    private final PaymentTransactionJpaRepository jpa;

    public PaymentTransactionRepositoryAdapter(PaymentTransactionJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<PaymentTransaction> findAllByOwnerId(Long ownerId) {
        return jpa.findAllByOwnerIdOrderByDateDesc(ownerId).stream().map(this::toDomain).toList();
    }

    @Override
    public boolean existsByReservationId(Long reservationId) {
        return jpa.existsByReservationId(reservationId);
    }

    @Override
    public PaymentTransaction save(PaymentTransaction t) {
        return toDomain(jpa.save(new PaymentTransactionJpaEntity(t.id(), t.ownerId(), t.reservationId(),
                t.driverName(), t.garageName(), t.date(), t.durationMinutes(), t.amountReceived())));
    }

    private PaymentTransaction toDomain(PaymentTransactionJpaEntity e) {
        return new PaymentTransaction(e.getId(), e.getOwnerId(), e.getReservationId(), e.getDriverName(),
                e.getGarageName(), e.getDate(), e.getDurationMinutes(), e.getAmountReceived());
    }
}
