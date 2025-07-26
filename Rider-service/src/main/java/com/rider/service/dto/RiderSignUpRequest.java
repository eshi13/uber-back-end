package com.rider.service.dto;

import lombok.Data;

@Data
public class RiderSignUpRequest {

    private String name;

    private String email;

    private String password;

    private String phone;
}
