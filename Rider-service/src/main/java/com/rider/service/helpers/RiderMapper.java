package com.rider.service.helpers;

import com.rider.service.dto.RiderPreferenceDto;
import com.rider.service.dto.RiderResponse;
import com.rider.service.dto.RiderSignUpRequest;
import com.rider.service.model.Rider;
import com.rider.service.model.RiderPreference;
import org.springframework.stereotype.Component;

@Component
public class RiderMapper {


    public Rider toEntity(RiderSignUpRequest request) {
        Rider rider = new Rider();
        rider.setName(request.getName());
        rider.setEmail(request.getEmail());
        rider.setPassword(request.getPassword());
        rider.setPhoneNumber(request.getPhone());
        return rider;
    }

    public RiderResponse toDto(Rider rider) {
        RiderResponse dto = new RiderResponse();
        dto.setId(rider.getId());
        dto.setName(rider.getName());
        dto.setEmail(rider.getEmail());
        dto.setPhoneNumber(rider.getPhoneNumber());
        dto.setRating(rider.getRating());
        return dto;
    }

    public RiderPreferenceDto toDto(RiderPreference prefs) {
        RiderPreferenceDto dto = new RiderPreferenceDto();
        dto.setPreferredLanguage(prefs.getLanguage());
        dto.setPaymentMethod(prefs.getPaymentMethod());
        return dto;
    }

    public void updatePrefsEntity(RiderPreference prefs, RiderPreferenceDto dto) {
        prefs.setLanguage(dto.getPreferredLanguage());
        prefs.setPaymentMethod(dto.getPaymentMethod());
    }
}
