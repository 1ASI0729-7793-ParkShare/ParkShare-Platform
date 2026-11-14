package com.acme.parkshare.platform.reputationnotifications.application.internal.commandservices;

import com.acme.parkshare.platform.reputationnotifications.application.commands.CreateRatingCommand;
import com.acme.parkshare.platform.reputationnotifications.application.commandservices.RatingCommandFailure;
import com.acme.parkshare.platform.reputationnotifications.application.commandservices.RatingCommandService;
import com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates.Rating;
import com.acme.parkshare.platform.reputationnotifications.domain.model.aggregates.UserReputation;
import com.acme.parkshare.platform.reputationnotifications.domain.repositories.RatingRepository;
import com.acme.parkshare.platform.reputationnotifications.domain.repositories.UserReputationRepository;
import com.acme.parkshare.platform.shared.application.result.Result;
import com.acme.parkshare.platform.reputationnotifications.domain.model.events.RatingSubmittedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;

@Service
@Transactional
public class RatingCommandServiceImpl implements RatingCommandService {

    private final RatingRepository ratingRepository;
    private final UserReputationRepository userReputationRepository;
    private final Clock clock;
    private final ApplicationEventPublisher eventPublisher;

    public RatingCommandServiceImpl(RatingRepository ratingRepository,
                                    UserReputationRepository userReputationRepository,
                                    Clock clock,
                                    ApplicationEventPublisher eventPublisher) {
        this.ratingRepository = ratingRepository;
        this.userReputationRepository = userReputationRepository;
        this.clock = clock;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Result<Rating, RatingCommandFailure> handle(CreateRatingCommand command) {
        if (command.score() < 1 || command.score() > 5) {
            return Result.failure(new RatingCommandFailure.InvalidScore("Score must be between 1 and 5"));
        }
        Instant now = Instant.now(clock);
        var rating = Rating.create(
                command.bookingId(),
                command.reviewerId(),
                command.revieweeId(),
                command.score(),
                command.comment(),
                now
        );
        var savedRating = ratingRepository.save(rating);

        // Update or create UserReputation
        var rep = userReputationRepository.findByUserId(command.revieweeId())
                .orElseGet(() -> UserReputation.create(command.revieweeId(), now));
        userReputationRepository.save(rep.addRating(command.score(), now));
        eventPublisher.publishEvent(new RatingSubmittedEvent(savedRating.getId(), command.bookingId(),
                command.reviewerId(), command.revieweeId(), command.score(), now));

        return Result.success(savedRating);
    }
}
