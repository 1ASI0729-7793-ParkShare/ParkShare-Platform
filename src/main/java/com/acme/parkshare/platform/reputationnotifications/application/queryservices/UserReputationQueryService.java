package com.acme.parkshare.platform.reputationnotifications.application.queryservices;

import com.acme.parkshare.platform.reputationnotifications.application.queries.GetAllUserReputationsQuery;
import com.acme.parkshare.platform.reputationnotifications.application.queries.GetUserReputationByUserIdQuery;
import com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates.UserReputation;

import java.util.List;
import java.util.Optional;

public interface UserReputationQueryService {
    List<UserReputation> handle(GetAllUserReputationsQuery query);
    Optional<UserReputation> handle(GetUserReputationByUserIdQuery query);
}
