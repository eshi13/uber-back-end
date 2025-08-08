package com.notification.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripAcceptRequest {
    private Long riderId;
    private Long driverId;
}
