package com.rider.service.dto;

import lombok.Data;

@Data
public class RiderResponse {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private Float rating;
}
