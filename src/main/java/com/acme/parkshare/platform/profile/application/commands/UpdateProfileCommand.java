package com.acme.parkshare.platform.profile.application.commands;

public record UpdateProfileCommand(Long id, String fullName, String plate, String vehicleModel, String vehicleType) {}
