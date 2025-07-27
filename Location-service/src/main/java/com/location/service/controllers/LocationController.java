package com.location.service.controllers;

import com.location.service.dto.LocationUpdateRequest;
import com.location.service.dto.NearbyDriverResponse;
import com.location.service.model.NearByDriver;
import com.location.service.services.LocationService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/nearby")
    public ResponseEntity<List<NearbyDriverResponse>> getNearbyDrivers(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "3.0") double radiusKm) {

        List<NearByDriver> drivers = locationService.findNearByDrivers(lat, lng, radiusKm);
        List<NearbyDriverResponse> response = drivers.stream()
                .map(d -> new NearbyDriverResponse(d.getDriverId(), d.getDistance()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
