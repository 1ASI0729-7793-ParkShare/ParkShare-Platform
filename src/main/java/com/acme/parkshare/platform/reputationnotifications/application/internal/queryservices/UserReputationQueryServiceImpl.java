package com.acme.parkshare.platform.reputationnotifications.application.internal.queryservices;

import com.acme.parkshare.platform.reputationnotifications.application.queries.GetAllUserReputationsQuery;
import com.acme.parkshare.platform.reputationnotifications.application.queries.GetUserReputationByUserIdQuery;
import com.acme.parkshare.platform.reputationnotifications.application.queryservices.UserReputationQueryService;
import com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates.UserReputation;
import com.acme.parkshare.platform.reputationnotifications.domain.repositories.UserReputationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserReputationQueryServiceImpl implements UserReputationQueryService {

    private final UserReputationRepository repository;

    public UserReputationQueryServiceImpl(UserReputationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserReputation> handle(GetAllUserReputationsQuery query) {
        return repository.findAll();
    }

    @Override
    public Optional<UserReputation> handle(GetUserReputationByUserIdQuery query) {
        return repository.findByUserId(query.userId());
    }
}
