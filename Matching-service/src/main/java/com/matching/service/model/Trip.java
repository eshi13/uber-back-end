package com.matching.service.model;

import com.matching.service.model.enums.TripStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "trip")

public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long riderId;
    private Long driverId;

    private Double pickupLatitude;
    private Double pickupLongitude;

    private Double dropoffLatitude;
    private Double dropoffLongitude;

    @Enumerated(EnumType.STRING)
    private TripStatus status;

    private Instant startTime;
    private Instant endTime;

    private Double fare;
}
