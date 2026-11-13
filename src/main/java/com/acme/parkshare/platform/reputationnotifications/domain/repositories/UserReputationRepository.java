package com.acme.parkshare.platform.reputationnotifications.domain.repositories;

import com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates.UserReputation;
import java.util.List;
import java.util.Optional;

public interface UserReputationRepository {
    UserReputation save(UserReputation userReputation);
    Optional<UserReputation> findById(Long id);
    Optional<UserReputation> findByUserId(Long userId);
    List<UserReputation> findAll();
}
