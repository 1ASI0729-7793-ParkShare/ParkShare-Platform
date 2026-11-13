package com.acme.parkshare.platform.profile.application.commandservices;

import com.acme.parkshare.platform.profile.application.commands.CreateProfileCommand;
import com.acme.parkshare.platform.profile.application.commands.UpdateProfileCommand;
import com.acme.parkshare.platform.profile.domain.model.aggregates.Profile;
import com.acme.parkshare.platform.shared.application.result.Result;

public interface ProfileCommandService {
    Result<Profile, ProfileCommandFailure> handle(CreateProfileCommand command);
    Result<Profile, ProfileCommandFailure> handle(UpdateProfileCommand command);
}
