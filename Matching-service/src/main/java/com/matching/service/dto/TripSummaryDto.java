package com.matching.service.dto;

import com.matching.service.model.enums.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TripSummaryDto {
    private Long tripId;
    private Long riderId;
    private Long driverId;
    private TripStatus status;
    private Instant startTime;
    private Instant endTime;
    private double fare;
}
