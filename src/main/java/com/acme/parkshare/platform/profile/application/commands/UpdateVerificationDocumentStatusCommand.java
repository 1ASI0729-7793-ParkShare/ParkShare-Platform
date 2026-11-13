package com.acme.parkshare.platform.profile.application.commands;

public record UpdateVerificationDocumentStatusCommand(Long id, String newStatus) {}
