package com.acme.parkshare.platform.reputationnotifications.interfaces.rest.transform;

import com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates.UserReputation;
import com.acme.parkshare.platform.reputationnotifications.interfaces.rest.resources.UserReputationResource;

public class UserReputationResourceMapper {
    private UserReputationResourceMapper() {}

    public static UserReputationResource toResource(UserReputation r) {
        return new UserReputationResource(
                r.getId(),
                r.getUserId(),
                r.getAverageRating(),
                r.getTotalRatings()
        );
    }
}
