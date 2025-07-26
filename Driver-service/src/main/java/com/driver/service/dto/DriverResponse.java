package com.driver.service.dto;

import lombok.Data;

@Data
public class DriverResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private Float rating;
    private Boolean isAvailable;

    // Additional fields can be added as needed
}
