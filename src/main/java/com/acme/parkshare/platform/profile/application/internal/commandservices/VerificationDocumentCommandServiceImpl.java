package com.acme.parkshare.platform.profile.application.internal.commandservices;

import com.acme.parkshare.platform.profile.application.commandservices.VerificationDocumentCommandFailure;
import com.acme.parkshare.platform.profile.application.commandservices.VerificationDocumentCommandService;
import com.acme.parkshare.platform.profile.application.commands.CreateVerificationDocumentCommand;
import com.acme.parkshare.platform.profile.application.commands.UpdateVerificationDocumentStatusCommand;
import com.acme.parkshare.platform.profile.domain.model.aggregates.VerificationDocument;
import com.acme.parkshare.platform.profile.domain.repositories.ProfileRepository;
import com.acme.parkshare.platform.profile.domain.repositories.VerificationDocumentRepository;
import com.acme.parkshare.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.Set;

@Service
@Transactional
public class VerificationDocumentCommandServiceImpl implements VerificationDocumentCommandService {

    private static final Set<String> VALID_TYPES = Set.of("national-id", "driver-license", "vehicle-registration");
    private static final Set<String> VALID_STATUSES = Set.of("verified", "in-review", "rejected");

    private final VerificationDocumentRepository verificationDocumentRepository;
    private final ProfileRepository profileRepository;
    private final Clock clock;

    public VerificationDocumentCommandServiceImpl(VerificationDocumentRepository verificationDocumentRepository,
                                                   ProfileRepository profileRepository,
                                                   Clock clock) {
        this.verificationDocumentRepository = verificationDocumentRepository;
        this.profileRepository = profileRepository;
        this.clock = clock;
    }

    @Override
    public Result<VerificationDocument, VerificationDocumentCommandFailure> handle(CreateVerificationDocumentCommand command) {
        if (profileRepository.findById(command.profileId()).isEmpty()) {
            return Result.failure(new VerificationDocumentCommandFailure.ProfileNotFound(command.profileId()));
        }
        Instant now = Instant.now(clock);
        VerificationDocument doc = VerificationDocument.create(command.profileId(), command.type(), now);
        return Result.success(verificationDocumentRepository.save(doc));
    }

    @Override
    public Result<VerificationDocument, VerificationDocumentCommandFailure> handle(UpdateVerificationDocumentStatusCommand command) {
        if (!VALID_STATUSES.contains(command.newStatus())) {
            return Result.failure(new VerificationDocumentCommandFailure.InvalidStatus(command.newStatus()));
        }
        return verificationDocumentRepository.findById(command.id())
                .map(existing -> {
                    Instant now = Instant.now(clock);
                    VerificationDocument updated = switch (command.newStatus()) {
                        case "verified" -> existing.approve(now);
                        case "rejected" -> existing.reject(now);
                        case "in-review" -> existing.review(now);
                        default -> throw new IllegalStateException("Unexpected status: " + command.newStatus());
                    };
                    return Result.<VerificationDocument, VerificationDocumentCommandFailure>success(
                            verificationDocumentRepository.save(updated));
                })
                .orElseGet(() -> Result.failure(new VerificationDocumentCommandFailure.DocumentNotFound(command.id())));
    }
}
