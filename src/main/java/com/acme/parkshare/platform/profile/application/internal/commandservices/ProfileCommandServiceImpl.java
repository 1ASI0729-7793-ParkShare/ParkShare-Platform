package com.acme.parkshare.platform.profile.application.internal.commandservices;

import com.acme.parkshare.platform.profile.application.commandservices.ProfileCommandFailure;
import com.acme.parkshare.platform.profile.application.commandservices.ProfileCommandService;
import com.acme.parkshare.platform.profile.application.commands.CreateProfileCommand;
import com.acme.parkshare.platform.profile.application.commands.UpdateProfileCommand;
import com.acme.parkshare.platform.profile.domain.model.aggregates.Profile;
import com.acme.parkshare.platform.profile.domain.repositories.ProfileRepository;
import com.acme.parkshare.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.Set;

@Service
@Transactional
public class ProfileCommandServiceImpl implements ProfileCommandService {

    private static final Set<String> VALID_VEHICLE_TYPES = Set.of("sedan", "suv", "hatchback", "pickup", "van");

    private final ProfileRepository repository;
    private final Clock clock;

    public ProfileCommandServiceImpl(ProfileRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    @Override
    public Result<Profile, ProfileCommandFailure> handle(CreateProfileCommand command) {
        if (!VALID_VEHICLE_TYPES.contains(command.vehicleType())) {
            return Result.failure(new ProfileCommandFailure.InvalidVehicleType(command.vehicleType()));
        }
        var profile = Profile.create(command.fullName(), command.plate(),
                command.vehicleModel(), command.vehicleType(), Instant.now(clock));
        return Result.success(repository.save(profile));
    }

    @Override
    public Result<Profile, ProfileCommandFailure> handle(UpdateProfileCommand command) {
        if (!VALID_VEHICLE_TYPES.contains(command.vehicleType())) {
            return Result.failure(new ProfileCommandFailure.InvalidVehicleType(command.vehicleType()));
        }
        var existing = repository.findById(command.id());
        if (existing.isEmpty()) return Result.failure(new ProfileCommandFailure.NotFound());
        var now = Instant.now(clock);
        var updated = Profile.rehydrate(command.id(), command.fullName(), command.plate(),
                command.vehicleModel(), command.vehicleType(), existing.get().getCreatedAt(), now);
        return Result.success(repository.save(updated));
    }
}
