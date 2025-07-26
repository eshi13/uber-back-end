package com.driver.service.dto;

import lombok.Data;

@Data
public class DriverVehicleRequest {
    private String vehicleMake;
    private String vehicleModel;
    private String vehicleNumber;
    private String vehicleColor;
    private String vehicleType;
}
