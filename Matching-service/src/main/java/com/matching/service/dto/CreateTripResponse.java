package com.matching.service.dto;

import com.matching.service.model.enums.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTripResponse {

    private Long tripId;
    private TripStatus status;
    private Instant startTime;
}
