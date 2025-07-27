package com.location.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "driver_location_history")
public class DriverLocationHistory {
    @Id
    private String id;
    private Long driverId;
    private double latitude;
    private double longitude;
    private Instant timestamp; // ISO 8601 format or any preferred format for date-time
}
