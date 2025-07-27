package com.location.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NearByDriver {
    private Long driverId;
    private double distance; // in kilometers
}