package com.acme.parkshare.platform.reputationnotifications.application.commandservices;

public sealed interface RatingCommandFailure permits
        RatingCommandFailure.NotFound,
        RatingCommandFailure.InvalidScore {
    record NotFound() implements RatingCommandFailure {}
    record InvalidScore(String reason) implements RatingCommandFailure {}
}
