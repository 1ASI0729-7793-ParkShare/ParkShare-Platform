package com.acme.parkshare.platform.profile.application.internal.queryservices;

import com.acme.parkshare.platform.profile.application.queries.GetAllProfilesQuery;
import com.acme.parkshare.platform.profile.application.queries.GetProfileByIdQuery;
import com.acme.parkshare.platform.profile.application.queryservices.ProfileQueryService;
import com.acme.parkshare.platform.profile.domain.model.aggregates.Profile;
import com.acme.parkshare.platform.profile.domain.repositories.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProfileQueryServiceImpl implements ProfileQueryService {
    private final ProfileRepository repository;
    public ProfileQueryServiceImpl(ProfileRepository repository) { this.repository = repository; }
    @Override public List<Profile> handle(GetAllProfilesQuery query) { return repository.findAll(); }
    @Override public Optional<Profile> handle(GetProfileByIdQuery query) { return repository.findById(query.id()); }
}
