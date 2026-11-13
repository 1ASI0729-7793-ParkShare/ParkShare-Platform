package com.acme.parkshare.platform.reputationnotifications.interfaces.rest.resources;

import java.math.BigDecimal;

public record UserReputationResource(
        Long id,
        Long userId,
        BigDecimal averageRating,
        int totalRatings
) {}
