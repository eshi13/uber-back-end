package com.location.service.dto;

import lombok.Data;

@Data
public class LocationUpdateRequest {

    private Long driverId;

    private double latitude;

    private double longitude;
}
