package com.acme.parkshare.platform.reputationnotifications.application.internal.queryservices;

import com.acme.parkshare.platform.reputationnotifications.application.queries.GetAllRatingsQuery;
import com.acme.parkshare.platform.reputationnotifications.application.queries.GetRatingByIdQuery;
import com.acme.parkshare.platform.reputationnotifications.application.queryservices.RatingQueryService;
import com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates.Rating;
import com.acme.parkshare.platform.reputationnotifications.domain.repositories.RatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RatingQueryServiceImpl implements RatingQueryService {

    private final RatingRepository repository;

    public RatingQueryServiceImpl(RatingRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Rating> handle(GetAllRatingsQuery query) {
        return repository.findAll();
    }

    @Override
    public Optional<Rating> handle(GetRatingByIdQuery query) {
        return repository.findById(query.id());
    }
}
