package com.location.service.controllers;

import com.location.service.dto.LocationUpdateRequest;
import com.location.service.dto.NearbyDriverResponse;
import com.location.service.dto.RiderLocationRequest;
import com.location.service.dto.RiderLocationResponse;
import com.location.service.model.NearByDriver;
import com.location.service.services.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/update")
    public ResponseEntity<Void> updateLocation(@RequestBody LocationUpdateRequest request) {
        locationService.updateDriverLocation(request.getDriverId(),
                request.getLatitude(), request.getLongitude());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/rider/update")
    public ResponseEntity<String> updateRiderLocation(@RequestBody RiderLocationRequest request) {
        locationService.saveRiderLocation(request.getRiderId(), request.getLatitude(), request.getLongitude());
        return ResponseEntity.ok("Rider location saved");
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<NearbyDriverResponse>> getNearbyDrivers(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "3.0") double radiusKm) {

        List<NearbyDriverResponse> drivers = locationService.findNearByDrivers(lat, lng, radiusKm);
        List<NearbyDriverResponse> response = drivers.stream()
                .map(d -> new NearbyDriverResponse(d.getDriverId(), d.getDistanceInKm()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/rider/{riderId}")
    public ResponseEntity<RiderLocationResponse> getRiderLocation(@PathVariable Long riderId) {
        RiderLocationResponse response = locationService.getRiderLocation(riderId);
        if(response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(response);
    }
}
