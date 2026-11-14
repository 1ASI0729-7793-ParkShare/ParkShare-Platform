package com.acme.parkshare.platform.payments.application.commandservices;

import com.acme.parkshare.platform.payments.application.commands.RequestOwnerTransferCommand;
import com.acme.parkshare.platform.payments.domain.model.aggregates.OwnerPayout;
import com.acme.parkshare.platform.booking.domain.model.events.ReservationCompletedEvent;
import com.acme.parkshare.platform.shared.application.result.Result;

public interface PaymentsCommandService {
    /** Records the earning of a completed reservation and credits the owner. Idempotent per reservation. */
    void handle(ReservationCompletedEvent event);

    Result<OwnerPayout, PaymentsCommandFailure> handle(RequestOwnerTransferCommand command);
}
