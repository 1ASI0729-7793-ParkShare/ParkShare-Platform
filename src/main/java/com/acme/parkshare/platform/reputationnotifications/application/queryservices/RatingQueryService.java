package com.acme.parkshare.platform.reputationnotifications.application.queryservices;

import com.acme.parkshare.platform.reputationnotifications.application.queries.GetAllRatingsQuery;
import com.acme.parkshare.platform.reputationnotifications.application.queries.GetRatingByIdQuery;
import com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingQueryService {
    List<Rating> handle(GetAllRatingsQuery query);
    Optional<Rating> handle(GetRatingByIdQuery query);
}
