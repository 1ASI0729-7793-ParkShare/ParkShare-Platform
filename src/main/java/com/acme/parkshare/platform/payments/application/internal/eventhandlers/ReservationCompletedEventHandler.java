package com.acme.parkshare.platform.payments.application.internal.eventhandlers;

import com.acme.parkshare.platform.payments.application.commandservices.PaymentsCommandService;
import com.acme.parkshare.platform.booking.domain.model.events.ReservationCompletedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/** Payments reacts to Booking: a completed reservation becomes an earning for the owner. */
@Component
public class ReservationCompletedEventHandler {

    private final PaymentsCommandService commandService;

    public ReservationCompletedEventHandler(PaymentsCommandService commandService) {
        this.commandService = commandService;
    }

    @EventListener
    public void on(ReservationCompletedEvent event) {
        commandService.handle(event);
    }
}
