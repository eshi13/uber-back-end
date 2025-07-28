package com.matching.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RiderLocationRequest {
    private Long riderId;
    private double latitude;
    private double longitude;
}
