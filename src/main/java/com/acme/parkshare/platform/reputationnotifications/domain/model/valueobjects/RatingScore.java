package com.acme.parkshare.platform.reputationnotifications.domain.model.valueobjects;

public record RatingScore(int value) {
    public RatingScore {
        if (value < 1 || value > 5) {
            throw new IllegalArgumentException("Rating score must be an integer between 1 and 5.");
        }
    }
}
