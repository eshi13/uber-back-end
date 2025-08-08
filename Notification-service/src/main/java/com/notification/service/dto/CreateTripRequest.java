package com.notification.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTripRequest {
    private Long riderId;
    private Long driverId;
    private double pickupLat;
    private double pickupLng;
}