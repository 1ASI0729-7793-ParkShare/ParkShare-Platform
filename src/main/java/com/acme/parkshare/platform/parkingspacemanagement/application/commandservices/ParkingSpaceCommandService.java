package com.acme.parkshare.platform.parkingspacemanagement.application.commandservices;

import com.acme.parkshare.platform.parkingspacemanagement.application.commands.CreateParkingSpaceCommand;
import com.acme.parkshare.platform.parkingspacemanagement.application.commands.DeleteParkingSpaceCommand;
import com.acme.parkshare.platform.parkingspacemanagement.application.commands.UpdateParkingSpaceCommand;
import com.acme.parkshare.platform.parkingspacemanagement.domain.model.aggregates.ParkingSpace;
import com.acme.parkshare.platform.shared.application.result.Result;

public interface ParkingSpaceCommandService {
    Result<ParkingSpace, ParkingSpaceCommandFailure> handle(CreateParkingSpaceCommand command);
    Result<ParkingSpace, ParkingSpaceCommandFailure> handle(UpdateParkingSpaceCommand command);
    Result<Void, ParkingSpaceCommandFailure> handle(DeleteParkingSpaceCommand command);
}
