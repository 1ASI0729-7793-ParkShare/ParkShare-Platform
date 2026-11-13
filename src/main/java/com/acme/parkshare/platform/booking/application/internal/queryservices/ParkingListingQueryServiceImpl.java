package com.acme.parkshare.platform.booking.application.internal.queryservices;

import com.acme.parkshare.platform.booking.application.queries.GetAllParkingListingsQuery;
import com.acme.parkshare.platform.booking.application.queries.GetParkingListingByIdQuery;
import com.acme.parkshare.platform.booking.application.queryservices.ParkingListingQueryService;
import com.acme.parkshare.platform.booking.domain.model.aggregates.ParkingListing;
import com.acme.parkshare.platform.booking.domain.repositories.ParkingListingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ParkingListingQueryServiceImpl implements ParkingListingQueryService {

    private final ParkingListingRepository repository;

    public ParkingListingQueryServiceImpl(ParkingListingRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ParkingListing> handle(GetAllParkingListingsQuery query) {
        return repository.findAll();
    }

    @Override
    public Optional<ParkingListing> handle(GetParkingListingByIdQuery query) {
        return repository.findById(query.id());
    }
}
