package com.acme.parkshare.platform.profile.infrastructure.persistence.jpa;

import com.acme.parkshare.platform.profile.infrastructure.persistence.jpa.entities.ProfileJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileJpaRepository extends JpaRepository<ProfileJpaEntity, Long> {}
