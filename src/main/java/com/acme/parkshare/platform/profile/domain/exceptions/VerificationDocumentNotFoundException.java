package com.acme.parkshare.platform.profile.domain.exceptions;

public class VerificationDocumentNotFoundException extends RuntimeException {
    public VerificationDocumentNotFoundException(Long id) {
        super("VerificationDocument not found with id: " + id);
    }
}
