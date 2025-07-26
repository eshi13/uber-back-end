package com.driver.service.services;

import com.driver.service.dto.DriverVehicleRequest;
import com.driver.service.helpers.DriverMapper;
import com.driver.service.models.Driver;
import com.driver.service.models.DriverVehicle;
import com.driver.service.repositories.DriverRepository;
import com.driver.service.repositories.DriverVehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    private final DriverVehicleRepository driverVehicleRepository;
    public Driver createDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public Optional<Driver> getDriverById(Long driverId) {
        return driverRepository.findById(driverId);
    }

    public Driver updateDriver(Long driverId, Driver updated) {
        return driverRepository.findById(driverId)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setPhoneNumber(updated.getPhoneNumber());
                    return driverRepository.save(existing);
                })
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));
    }

    public void setAvailability(Long driverId, boolean isAvailable) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));
        driver.setIsAvailable(isAvailable);
        driverRepository.save(driver);
    }

    public DriverVehicle upsertVehicle(Long driverId, DriverVehicleRequest request) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));
        DriverVehicle vehicle = driverVehicleRepository.findById(driverId)
                .orElseGet(() -> {
                    DriverVehicle v = new DriverVehicle();
                    v.setDriver(driver);
                    return v;
                });

        vehicle.setVehicleMake(request.getVehicleMake());
        vehicle.setVehicleModel(request.getVehicleModel());
        vehicle.setVehicleNumber(request.getVehicleNumber());
        vehicle.setVehicleType(request.getVehicleType());

        return driverVehicleRepository.save(vehicle);
    }

    public Optional<DriverVehicle> getVehicleByDriverId(Long driverId) {
        return driverVehicleRepository.findById(driverId);
    }
}
