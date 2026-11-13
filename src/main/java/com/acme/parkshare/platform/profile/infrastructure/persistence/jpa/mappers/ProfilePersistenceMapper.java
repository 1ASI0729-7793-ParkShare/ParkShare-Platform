package com.acme.parkshare.platform.profile.infrastructure.persistence.jpa.mappers;

import com.acme.parkshare.platform.profile.domain.model.aggregates.Profile;
import com.acme.parkshare.platform.profile.infrastructure.persistence.jpa.entities.ProfileJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class ProfilePersistenceMapper {
    public ProfileJpaEntity toEntity(Profile p) {
        return new ProfileJpaEntity(p.getId(), p.getFullName(), p.getPlate(),
                p.getVehicleModel(), p.getVehicleType(), p.getCreatedAt(), p.getUpdatedAt());
    }
    public Profile toDomain(ProfileJpaEntity e) {
        return Profile.rehydrate(e.getId(), e.getFullName(), e.getPlate(),
                e.getVehicleModel(), e.getVehicleType(), e.getCreatedAt(), e.getUpdatedAt());
    }
}
