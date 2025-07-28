package com.location.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiderLocationResponse {
    private Long riderId;
    private Double latitude;
    private Double longitude;
}
