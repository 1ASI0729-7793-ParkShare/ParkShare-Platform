package com.acme.parkshare.platform.profile.application.commands;

public record CreateVerificationDocumentCommand(Long profileId, String type) {}
