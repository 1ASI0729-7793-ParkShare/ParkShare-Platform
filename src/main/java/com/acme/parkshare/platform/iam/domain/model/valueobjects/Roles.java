package com.acme.parkshare.platform.iam.domain.model.valueobjects;

/**
 * Roles
 * <p>
 *     Roles of the ParkShare platform: a driver looks for and books parking spaces,
 *     an owner publishes spaces, and an admin manages the platform.
 * </p>
 */
public enum Roles {
    ROLE_DRIVER,
    ROLE_OWNER,
    ROLE_ADMIN
}
