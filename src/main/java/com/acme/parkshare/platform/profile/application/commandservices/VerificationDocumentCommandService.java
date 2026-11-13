package com.acme.parkshare.platform.profile.application.commandservices;

import com.acme.parkshare.platform.profile.application.commands.CreateVerificationDocumentCommand;
import com.acme.parkshare.platform.profile.application.commands.UpdateVerificationDocumentStatusCommand;
import com.acme.parkshare.platform.profile.domain.model.aggregates.VerificationDocument;
import com.acme.parkshare.platform.shared.application.result.Result;

public interface VerificationDocumentCommandService {
    Result<VerificationDocument, VerificationDocumentCommandFailure> handle(CreateVerificationDocumentCommand command);
    Result<VerificationDocument, VerificationDocumentCommandFailure> handle(UpdateVerificationDocumentStatusCommand command);
}
