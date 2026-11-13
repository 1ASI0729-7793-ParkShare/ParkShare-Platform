package com.acme.parkshare.platform.profile.application.internal.queryservices;

import com.acme.parkshare.platform.profile.application.queries.GetAllVerificationDocumentsQuery;
import com.acme.parkshare.platform.profile.application.queries.GetVerificationDocumentByIdQuery;
import com.acme.parkshare.platform.profile.application.queryservices.VerificationDocumentQueryService;
import com.acme.parkshare.platform.profile.domain.model.aggregates.VerificationDocument;
import com.acme.parkshare.platform.profile.domain.repositories.VerificationDocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class VerificationDocumentQueryServiceImpl implements VerificationDocumentQueryService {
    private final VerificationDocumentRepository repository;
    public VerificationDocumentQueryServiceImpl(VerificationDocumentRepository repository) { this.repository = repository; }
    @Override public List<VerificationDocument> handle(GetAllVerificationDocumentsQuery query) { return repository.findAll(); }
    @Override public Optional<VerificationDocument> handle(GetVerificationDocumentByIdQuery query) { return repository.findById(query.id()); }
}
