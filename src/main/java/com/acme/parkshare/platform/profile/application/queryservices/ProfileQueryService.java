package com.acme.parkshare.platform.profile.application.queryservices;

import com.acme.parkshare.platform.profile.application.queries.GetAllProfilesQuery;
import com.acme.parkshare.platform.profile.application.queries.GetProfileByIdQuery;
import com.acme.parkshare.platform.profile.domain.model.aggregates.Profile;

import java.util.List;
import java.util.Optional;

public interface ProfileQueryService {
    List<Profile> handle(GetAllProfilesQuery query);
    Optional<Profile> handle(GetProfileByIdQuery query);
}
