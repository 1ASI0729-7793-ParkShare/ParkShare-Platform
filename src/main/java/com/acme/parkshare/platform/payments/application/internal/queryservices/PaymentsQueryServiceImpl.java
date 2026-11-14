package com.acme.parkshare.platform.payments.application.internal.queryservices;

import com.acme.parkshare.platform.payments.application.queries.GetOwnerPayoutByOwnerIdQuery;
import com.acme.parkshare.platform.payments.application.queries.GetTransactionsByOwnerIdQuery;
import com.acme.parkshare.platform.payments.application.queryservices.PaymentsQueryService;
import com.acme.parkshare.platform.payments.domain.model.aggregates.OwnerPayout;
import com.acme.parkshare.platform.payments.domain.model.aggregates.PaymentTransaction;
import com.acme.parkshare.platform.payments.domain.repositories.OwnerPayoutRepository;
import com.acme.parkshare.platform.payments.domain.repositories.PaymentTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PaymentsQueryServiceImpl implements PaymentsQueryService {

    private final OwnerPayoutRepository payouts;
    private final PaymentTransactionRepository transactions;

    public PaymentsQueryServiceImpl(OwnerPayoutRepository payouts, PaymentTransactionRepository transactions) {
        this.payouts = payouts;
        this.transactions = transactions;
    }

    /** An owner with no earnings yet has an empty summary; it is created on the first credit. */
    @Override
    public OwnerPayout handle(GetOwnerPayoutByOwnerIdQuery query) {
        return payouts.findByOwnerId(query.ownerId()).orElseGet(() -> payouts.save(OwnerPayout.openFor(query.ownerId())));
    }

    @Override
    public List<PaymentTransaction> handle(GetTransactionsByOwnerIdQuery query) {
        return transactions.findAllByOwnerId(query.ownerId());
    }
}
