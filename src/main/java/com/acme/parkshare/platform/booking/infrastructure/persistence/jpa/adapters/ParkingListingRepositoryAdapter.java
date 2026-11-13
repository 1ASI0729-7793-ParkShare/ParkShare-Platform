package com.acme.parkshare.platform.booking.infrastructure.persistence.jpa.adapters;

import com.acme.parkshare.platform.booking.domain.model.aggregates.ParkingListing;
import com.acme.parkshare.platform.booking.domain.repositories.ParkingListingRepository;
import com.acme.parkshare.platform.booking.infrastructure.persistence.jpa.ParkingListingJpaRepository;
import com.acme.parkshare.platform.booking.infrastructure.persistence.jpa.mappers.ParkingListingPersistenceMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ParkingListingRepositoryAdapter implements ParkingListingRepository {

    private final ParkingListingJpaRepository jpaRepository;
    private final ParkingListingPersistenceMapper mapper;

    public ParkingListingRepositoryAdapter(ParkingListingJpaRepository jpaRepository,
                                           ParkingListingPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ParkingListing save(ParkingListing listing) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(listing)));
    }

    @Override
    public Optional<ParkingListing> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<ParkingListing> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<ParkingListing> findAllByOwnerId(Long ownerId) {
        return jpaRepository.findAllByOwnerId(ownerId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
