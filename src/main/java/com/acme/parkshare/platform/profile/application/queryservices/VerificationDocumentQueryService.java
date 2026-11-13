package com.acme.parkshare.platform.profile.application.queryservices;

import com.acme.parkshare.platform.profile.application.queries.GetAllVerificationDocumentsQuery;
import com.acme.parkshare.platform.profile.application.queries.GetVerificationDocumentByIdQuery;
import com.acme.parkshare.platform.profile.domain.model.aggregates.VerificationDocument;

import java.util.List;
import java.util.Optional;

public interface VerificationDocumentQueryService {
    List<VerificationDocument> handle(GetAllVerificationDocumentsQuery query);
    Optional<VerificationDocument> handle(GetVerificationDocumentByIdQuery query);
}
