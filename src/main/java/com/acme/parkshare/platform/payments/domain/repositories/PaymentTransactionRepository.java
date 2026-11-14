package com.acme.parkshare.platform.payments.domain.repositories;

import com.acme.parkshare.platform.payments.domain.model.aggregates.PaymentTransaction;

import java.util.List;

public interface PaymentTransactionRepository {
    List<PaymentTransaction> findAllByOwnerId(Long ownerId);
    boolean existsByReservationId(Long reservationId);
    PaymentTransaction save(PaymentTransaction transaction);
}
