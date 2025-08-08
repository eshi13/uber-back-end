package com.notification.service.controller;

import com.notification.service.client.TripServiceClient;
import com.notification.service.dto.CreateTripRequest;
import com.notification.service.dto.CreateTripResponse;
import com.notification.service.dto.TripNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/notify")
@RequiredArgsConstructor
public class NotificationController {
    private final RedisTemplate<String, Object> redisTemplate;
    private final TripServiceClient tripServiceClient;

    @PostMapping("/driver")
    public ResponseEntity<String> notifyDriver(@RequestBody TripNotificationRequest request) {
        String key = "trip:offer:" + request.getDriverId();
        redisTemplate.opsForValue().set(key, request, Duration.ofMinutes(1));
        return ResponseEntity.ok("Driver notified");
    }

    @PostMapping("/driver/accept")
    public ResponseEntity<String> acceptTrip(@RequestBody TripNotificationRequest request) {
        String key = "trip:offer:" + request.getRiderId();
        TripNotificationRequest offer = (TripNotificationRequest) redisTemplate.opsForValue().get(key);
        if (offer == null || !offer.getDriverId().equals(request.getDriverId())) {
            return ResponseEntity.badRequest().body("Trip offer expired");
        }

        CreateTripRequest tripRequest = CreateTripRequest.builder()
                .riderId(offer.getRiderId())
                .driverId(offer.getDriverId())
                .pickupLat(offer.getPickupLat())
                .pickupLng(offer.getPickupLng())
                .build();
        CreateTripResponse tripResponse = tripServiceClient.createTrip(tripRequest).getBody();
        return ResponseEntity.ok("Trip created: " + tripResponse.getTripId());
    }

}
