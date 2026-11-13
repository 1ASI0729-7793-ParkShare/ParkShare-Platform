package com.acme.parkshare.platform.parkingspacemanagement.infrastructure.persistence.jpa.adapters;

import com.acme.parkshare.platform.parkingspacemanagement.domain.model.aggregates.ParkingSpace;
import com.acme.parkshare.platform.parkingspacemanagement.domain.repositories.ParkingSpaceRepository;
import com.acme.parkshare.platform.parkingspacemanagement.infrastructure.persistence.jpa.ParkingSpaceJpaRepository;
import com.acme.parkshare.platform.parkingspacemanagement.infrastructure.persistence.jpa.mappers.ParkingSpacePersistenceMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ParkingSpaceRepositoryAdapter implements ParkingSpaceRepository {

    private final ParkingSpaceJpaRepository jpaRepository;
    private final ParkingSpacePersistenceMapper mapper;

    public ParkingSpaceRepositoryAdapter(ParkingSpaceJpaRepository jpaRepository,
                                         ParkingSpacePersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ParkingSpace save(ParkingSpace space) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(space)));
    }

    @Override
    public Optional<ParkingSpace> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<ParkingSpace> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<ParkingSpace> findAllByOwnerId(Long ownerId) {
        return jpaRepository.findAllByOwnerId(ownerId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
