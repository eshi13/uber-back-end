package com.rider.service.dto;

import com.rider.service.model.enums.PreferredLanguage;
import lombok.Data;

@Data
public class RiderPreferenceDto {
    private PreferredLanguage preferredLanguage;
    private String paymentMethod;

}
