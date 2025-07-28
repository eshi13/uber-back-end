package com.matching.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchAcceptRequest {

    private Long riderId;
    private Long driverId;
}
