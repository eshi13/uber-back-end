package com.driver.service.helpers;

import com.driver.service.dto.DriverResponse;
import com.driver.service.dto.DriverSignUpRequest;
import com.driver.service.dto.DriverVehicleRequest;
import com.driver.service.dto.DriverVehicleResponse;
import com.driver.service.models.Driver;
import com.driver.service.models.DriverVehicle;
import org.springframework.stereotype.Component;

@Component
public class DriverMapper {

    public Driver toEntity(DriverSignUpRequest request) {
        Driver driver = new Driver();
        driver.setName(request.getName());
        driver.setName(request.getName());
        driver.setEmail(request.getEmail());
        driver.setPassword(request.getPassword());
        driver.setPhoneNumber(request.getPhoneNumber());
        driver.setIsAvailable(true);
        DriverVehicle driverVehicle = new DriverVehicle();
        driverVehicle.setVehicleColor(request.getVehicle().getVehicleColor());
        driverVehicle.setVehicleMake(request.getVehicle().getVehicleMake());
        driverVehicle.setVehicleModel(request.getVehicle().getVehicleModel());
        driverVehicle.setVehicleNumber(request.getVehicle().getVehicleNumber());
        driverVehicle.setVehicleType(request.getVehicle().getVehicleType());
        driver.setVehicle(driverVehicle);
        return driver;
    }

    public DriverResponse toDto(Driver driver) {
        DriverResponse response = new DriverResponse();
        response.setId(driver.getId());
        response.setName(driver.getName());
        response.setEmail(driver.getEmail());
        response.setPhoneNumber(driver.getPhoneNumber());
        response.setRating(driver.getRating());
        response.setIsAvailable(driver.getIsAvailable());
        return response;
    }

    public DriverVehicleResponse toDto(DriverVehicle vehicle) {
        DriverVehicleResponse dto = new DriverVehicleResponse();
        dto.setVehiceleMake(vehicle.getVehicleMake());
        dto.setVehicleModel(vehicle.getVehicleModel());
        dto.setVehicleNumber(vehicle.getVehicleNumber());
        dto.setVehicleType(vehicle.getVehicleType());
        dto.setRegistrationDate(vehicle.getRegistrationDate());
        return dto;
    }

    public void updateEntity(DriverVehicle vehicle, DriverVehicleRequest req) {
        vehicle.setVehicleMake(req.getVehicleMake());
        vehicle.setVehicleModel(req.getVehicleModel());
        vehicle.setVehicleNumber(req.getVehicleNumber());
        vehicle.setVehicleType(req.getVehicleType());
    }
}
