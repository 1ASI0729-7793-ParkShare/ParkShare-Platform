package com.acme.parkshare.platform.reputationnotifications.domain.repositories;

import com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates.Rating;
import java.util.List;
import java.util.Optional;

public interface RatingRepository {
    Rating save(Rating rating);
    Optional<Rating> findById(Long id);
    List<Rating> findAll();
    List<Rating> findAllByRevieweeId(Long revieweeId);
}
