package com.acme.parkshare.platform.profile.interfaces.rest.transform;

import com.acme.parkshare.platform.profile.domain.model.aggregates.Profile;
import com.acme.parkshare.platform.profile.interfaces.rest.resources.ProfileResource;

public class ProfileResourceMapper {
    private ProfileResourceMapper() {}
    public static ProfileResource toResource(Profile p) {
        return new ProfileResource(p.getId(), p.getFullName(), p.getPlate(),
                p.getVehicleModel(), p.getVehicleType(), p.getCreatedAt(), p.getUpdatedAt());
    }
}
