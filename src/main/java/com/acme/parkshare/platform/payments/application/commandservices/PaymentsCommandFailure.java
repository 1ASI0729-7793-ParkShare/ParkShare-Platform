package com.acme.parkshare.platform.payments.application.commandservices;

public sealed interface PaymentsCommandFailure
        permits PaymentsCommandFailure.NotFound, PaymentsCommandFailure.NothingToTransfer {

    record NotFound() implements PaymentsCommandFailure {}

    record NothingToTransfer() implements PaymentsCommandFailure {}
}
