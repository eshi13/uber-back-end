package com.rider.service.controllers;

import com.rider.service.dto.RiderPreferenceDto;
import com.rider.service.dto.RiderResponse;
import com.rider.service.dto.RiderSignUpRequest;
import com.rider.service.helpers.RiderMapper;
import com.rider.service.model.Rider;
import com.rider.service.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/riders")
@RequiredArgsConstructor
public class RiderController {

    private final RiderService riderService;
    private final RiderMapper riderMapper;

    @PostMapping("/signup")
    public ResponseEntity<RiderResponse> signup(@RequestBody RiderSignUpRequest request) {
        Rider rider = riderMapper.toEntity(request);
        Rider saved = riderService.createRider(rider);
        return new ResponseEntity<>(riderMapper.toDto(saved), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RiderResponse> getProfile(@PathVariable Long id) {
        return riderService.getRiderById(id)
                .map(rider -> new ResponseEntity<>(riderMapper.toDto(rider), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}/preferences")
    public ResponseEntity<RiderPreferenceDto> updatePrefs(
            @PathVariable Long id,
            @RequestBody RiderPreferenceDto prefsDto
    ) {
        Rider rider = riderService.getRiderById(id)
                .orElseThrow(() -> new RuntimeException("Rider not found with id: " + id));

        riderMapper.updatePrefsEntity(rider.getPreferences(), prefsDto);
        Rider updated = riderService.updateRider(id, rider);

        return new ResponseEntity<>(riderMapper.toDto(updated.getPreferences()), HttpStatus.OK);
    }
}
