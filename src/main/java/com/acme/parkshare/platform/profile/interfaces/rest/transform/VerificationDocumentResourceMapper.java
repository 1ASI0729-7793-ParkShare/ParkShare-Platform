package com.acme.parkshare.platform.profile.interfaces.rest.transform;

import com.acme.parkshare.platform.profile.domain.model.aggregates.VerificationDocument;
import com.acme.parkshare.platform.profile.interfaces.rest.resources.VerificationDocumentResource;

public class VerificationDocumentResourceMapper {
    private VerificationDocumentResourceMapper() {}
    public static VerificationDocumentResource toResource(VerificationDocument d) {
        return new VerificationDocumentResource(d.getId(), d.getProfileId(), d.getType(),
                d.getStatus(), d.getFileName(), d.getCreatedAt(), d.getUpdatedAt());
    }
}
