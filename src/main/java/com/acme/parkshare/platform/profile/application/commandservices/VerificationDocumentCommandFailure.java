package com.acme.parkshare.platform.profile.application.commandservices;

public sealed interface VerificationDocumentCommandFailure permits
        VerificationDocumentCommandFailure.DocumentNotFound,
        VerificationDocumentCommandFailure.InvalidStatus,
        VerificationDocumentCommandFailure.ProfileNotFound {

    record DocumentNotFound(Long id) implements VerificationDocumentCommandFailure {}
    record InvalidStatus(String status) implements VerificationDocumentCommandFailure {}
    record ProfileNotFound(Long profileId) implements VerificationDocumentCommandFailure {}
}
