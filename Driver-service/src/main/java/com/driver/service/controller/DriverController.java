package com.driver.service.controller;

import com.driver.service.dto.DriverResponse;
import com.driver.service.dto.DriverSignUpRequest;
import com.driver.service.dto.DriverVehicleRequest;
import com.driver.service.dto.DriverVehicleResponse;
import com.driver.service.helpers.DriverMapper;
import com.driver.service.models.Driver;
import com.driver.service.models.DriverVehicle;
import com.driver.service.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;
    private final DriverMapper driverMapper;

    // Driver Signup
    @PostMapping("/signup")
    public ResponseEntity<DriverResponse> signup(@RequestBody DriverSignUpRequest request) {
        Driver driver = driverMapper.toEntity(request);
        Driver saved = driverService.createDriver(driver);
        return new ResponseEntity<>(driverMapper.toDto(saved), HttpStatus.CREATED);
    }

    //Get Driver Profile
    @GetMapping("/{id}")
    public ResponseEntity<DriverResponse> getDriver(@PathVariable Long id) {
        return driverService.getDriverById(id)
                .map(driverMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //update Driver profile
    @PutMapping("/{id}")
    public ResponseEntity<DriverResponse> updateDriver(
            @PathVariable Long id,
            @RequestBody DriverSignUpRequest updateRequest) {
        Driver updatedDriver = driverMapper.toEntity(updateRequest);
        Driver saved = driverService.updateDriver(id, updatedDriver);
        return ResponseEntity.ok(driverMapper.toDto(saved));
    }

    //toggle availability
    @PutMapping("/{id}/availability")
    public ResponseEntity<Void> setAvailability(
            @PathVariable Long id,
            @RequestParam boolean isAvailable
    ) {
        driverService.setAvailability(id, isAvailable);
        return ResponseEntity.ok().build();
    }

    // Get Driver Vehicle
    @GetMapping("/{id}/vehicle")
    public ResponseEntity<DriverVehicleResponse> getVehicle(@PathVariable Long id) {
        return driverService.getVehicleByDriverId(id)
                .map(driverMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Register/Update Vehicle
    @PutMapping("/{id}/vehicle")
    public ResponseEntity<DriverVehicleResponse> registerOrUpdateVehicle(
            @PathVariable Long id,
            @RequestBody DriverVehicleRequest request
    ) {
        DriverVehicle saved = driverService.upsertVehicle(id, request);
        return ResponseEntity.ok(driverMapper.toDto(saved));
    }

}
