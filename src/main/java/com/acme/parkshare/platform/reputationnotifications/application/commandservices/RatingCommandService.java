package com.acme.parkshare.platform.reputationnotifications.application.commandservices;

import com.acme.parkshare.platform.reputationnotifications.application.commands.CreateRatingCommand;
import com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates.Rating;
import com.acme.parkshare.platform.shared.application.result.Result;

public interface RatingCommandService {
    Result<Rating, RatingCommandFailure> handle(CreateRatingCommand command);
}
