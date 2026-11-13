package com.acme.parkshare.platform.profile.domain.repositories;

import com.acme.parkshare.platform.profile.domain.model.aggregates.Profile;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository {
    Profile save(Profile profile);
    Optional<Profile> findById(Long id);
    List<Profile> findAll();
}
