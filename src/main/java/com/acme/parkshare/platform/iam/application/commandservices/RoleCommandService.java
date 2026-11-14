package com.acme.parkshare.platform.iam.application.commandservices;

import com.acme.parkshare.platform.iam.application.commands.SeedRolesCommand;

/**
 * Application service contract for IAM role commands.
 */
public interface RoleCommandService {
    /**
     * Handles the role seeding command.
     *
     * @param command role-seeding command
     */
    void handle(SeedRolesCommand command);
}

