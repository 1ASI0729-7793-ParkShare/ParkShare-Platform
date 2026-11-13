package com.acme.parkshare.platform.parkingspacemanagement.application.internal.queryservices;

import com.acme.parkshare.platform.parkingspacemanagement.application.queries.GetAllParkingSpacesByOwnerIdQuery;
import com.acme.parkshare.platform.parkingspacemanagement.application.queries.GetAllParkingSpacesQuery;
import com.acme.parkshare.platform.parkingspacemanagement.application.queries.GetParkingSpaceByIdQuery;
import com.acme.parkshare.platform.parkingspacemanagement.application.queryservices.ParkingSpaceQueryService;
import com.acme.parkshare.platform.parkingspacemanagement.domain.model.aggregates.ParkingSpace;
import com.acme.parkshare.platform.parkingspacemanagement.domain.repositories.ParkingSpaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ParkingSpaceQueryServiceImpl implements ParkingSpaceQueryService {

    private final ParkingSpaceRepository repository;

    public ParkingSpaceQueryServiceImpl(ParkingSpaceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ParkingSpace> handle(GetAllParkingSpacesQuery query) {
        return repository.findAll();
    }

    @Override
    public Optional<ParkingSpace> handle(GetParkingSpaceByIdQuery query) {
        return repository.findById(query.id());
    }

    @Override
    public List<ParkingSpace> handle(GetAllParkingSpacesByOwnerIdQuery query) {
        return repository.findAllByOwnerId(query.ownerId());
    }
}
