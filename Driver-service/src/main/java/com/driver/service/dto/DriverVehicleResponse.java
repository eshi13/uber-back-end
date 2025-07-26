package com.driver.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DriverVehicleResponse {
    private String vehiceleMake;
    private String vehicleModel;
    private String vehicleNumber;
    private String vehicleColor;
    private String vehicleType;
    private LocalDateTime registrationDate;
}
