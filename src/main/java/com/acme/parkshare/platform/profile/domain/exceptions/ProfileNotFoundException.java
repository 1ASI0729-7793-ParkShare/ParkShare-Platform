package com.acme.parkshare.platform.profile.domain.exceptions;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException(Long id) {
        super("Profile not found with id: " + id);
    }
}
