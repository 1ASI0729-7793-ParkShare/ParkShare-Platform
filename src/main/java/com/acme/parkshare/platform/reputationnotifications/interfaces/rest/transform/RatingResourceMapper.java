package com.acme.parkshare.platform.reputationnotifications.interfaces.rest.transform;

import com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates.Rating;
import com.acme.parkshare.platform.reputationnotifications.interfaces.rest.resources.RatingResource;

public class RatingResourceMapper {
    private RatingResourceMapper() {}

    public static RatingResource toResource(Rating r) {
        return new RatingResource(
                r.getId(),
                r.getBookingId(),
                r.getReviewerId(),
                r.getRevieweeId(),
                r.getScore().value(),
                r.getComment(),
                r.getCreatedAt()
        );
    }
}
