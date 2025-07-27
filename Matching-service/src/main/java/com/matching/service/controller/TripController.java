package com.matching.service.controller;

import com.matching.service.dto.CreateTripRequest;
import com.matching.service.dto.CreateTripResponse;
import com.matching.service.model.Trip;
import com.matching.service.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @PostMapping("/create")
    public ResponseEntity<CreateTripResponse> createTrip(@RequestBody CreateTripRequest request) {
        CreateTripResponse response = tripService.createTrip(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable long id) {
        Trip trip = tripService.getTripById(id);
        return ResponseEntity.ok(trip);
    }

}
