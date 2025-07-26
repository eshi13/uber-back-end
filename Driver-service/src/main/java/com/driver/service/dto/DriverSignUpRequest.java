package com.driver.service.dto;

import lombok.Data;

@Data
public class DriverSignUpRequest {

    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private DriverVehicleRequest vehicle;
}
