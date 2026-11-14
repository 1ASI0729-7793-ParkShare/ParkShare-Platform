package com.acme.parkshare.platform.payments.application.internal.commandservices;

import com.acme.parkshare.platform.payments.application.commandservices.PaymentsCommandFailure;
import com.acme.parkshare.platform.payments.application.commandservices.PaymentsCommandService;
import com.acme.parkshare.platform.payments.application.commands.RequestOwnerTransferCommand;
import com.acme.parkshare.platform.payments.domain.model.aggregates.OwnerPayout;
import com.acme.parkshare.platform.payments.domain.model.aggregates.PaymentTransaction;
import com.acme.parkshare.platform.payments.domain.repositories.OwnerPayoutRepository;
import com.acme.parkshare.platform.payments.domain.repositories.PaymentTransactionRepository;
import com.acme.parkshare.platform.booking.domain.model.events.ReservationCompletedEvent;
import com.acme.parkshare.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@Transactional
public class PaymentsCommandServiceImpl implements PaymentsCommandService {

    private final OwnerPayoutRepository payouts;
    private final PaymentTransactionRepository transactions;

    public PaymentsCommandServiceImpl(OwnerPayoutRepository payouts, PaymentTransactionRepository transactions) {
        this.payouts = payouts;
        this.transactions = transactions;
    }

    @Override
    public void handle(ReservationCompletedEvent event) {
        if (event.totalCost() == null || transactions.existsByReservationId(event.reservationId())) {
            return;
        }
        var payout = payouts.findByOwnerId(event.ownerId()).orElseGet(() -> OwnerPayout.openFor(event.ownerId()));
        var net = payout.netOf(event.totalCost());
        payout.credit(net);
        payouts.save(payout);

        var minutes = event.startedAt() != null && event.endedAt() != null
                ? (int) Duration.between(event.startedAt(), event.endedAt()).toMinutes()
                : event.plannedHours() * 60;
        transactions.save(new PaymentTransaction(null, event.ownerId(), event.reservationId(),
                event.driverName(), event.spaceName(),
                event.endedAt() != null ? event.endedAt() : event.occurredOn(), minutes, net));
    }

    @Override
    public Result<OwnerPayout, PaymentsCommandFailure> handle(RequestOwnerTransferCommand command) {
        var found = payouts.findById(command.payoutId());
        if (found.isEmpty()) {
            return Result.failure(new PaymentsCommandFailure.NotFound());
        }
        var payout = found.get();
        try {
            payout.requestTransfer();
        } catch (IllegalStateException e) {
            return Result.failure(new PaymentsCommandFailure.NothingToTransfer());
        }
        return Result.success(payouts.save(payout));
    }
}
