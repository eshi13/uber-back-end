package com.matching.service.service;

import com.matching.service.clients.LocationServiceClient;
import com.matching.service.dto.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MatchingService {

    private final LocationServiceClient locationServiceClient;
    private final TripService tripService;

    public boolean acceptMatch(MatchAcceptRequest req) {
        CreateTripRequest trip = new CreateTripRequest();
        trip.setDriverId(req.getDriverId());
        trip.setRiderId(req.getRiderId());
        RiderLocationResponse riderLocationResponse= locationServiceClient.getRiderLocation(req.getRiderId());
        if (riderLocationResponse == null) {
            return false; // Rider location not found
        }
        trip.setPickupLongitude(riderLocationResponse.getLongitude());
        trip.setPickupLongitude(riderLocationResponse.getLongitude());
        log.info("Creating trip for rider {} and driver {}", req.getRiderId(), req.getDriverId());
        return true;

    }

    public MatchResponse matchRider(Long riderId) {
        RiderLocationResponse riderLocation = locationServiceClient.getRiderLocation(riderId);
        if (riderLocation == null) {
            throw new RuntimeException("Rider location not found");
        }

        List<NearbyDriverResponse> nearbyDrivers = locationServiceClient.findNearByDrivers(riderLocation.getLatitude(), riderLocation.getLongitude(), 3);

        NearbyDriverResponse selectedDriverId = nearbyDrivers.stream().findFirst().orElse(null);
        if (selectedDriverId == null) {
            return new MatchResponse(false, null, "No drivers available");
        }

        return new MatchResponse(true, selectedDriverId.getDriverId(), "Driver found");
    }
}
