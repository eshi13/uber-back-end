package com.matching.service.service;

import com.matching.service.dto.CreateTripRequest;
import com.matching.service.dto.CreateTripResponse;
import com.matching.service.model.Trip;
import com.matching.service.model.enums.TripStatus;
import com.matching.service.repository.TripRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class TripService {

    private final TripRepository tripRepository;

    public CreateTripResponse createTrip(CreateTripRequest request) {
        Trip trip = new Trip();
        trip.setDriverId(request.getDriverId());
        trip.setRiderId(request.getRiderId());
        trip.setPickupLatitude(request.getPickupLatitude());
        trip.setPickupLongitude(request.getPickupLongitude());
        trip.setStatus(TripStatus.REQUESTED);
        trip.setStartTime(Instant.now());

        Trip savedTrip = tripRepository.save(trip);
        return new CreateTripResponse(
                savedTrip.getId(),
                savedTrip.getStatus(),
                savedTrip.getStartTime()
        );
    }

    public Trip getTripById(Long id) {
        return tripRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found with id: " + id));
    }
}
