package com.driver.service.repositories;

import com.driver.service.models.DriverVehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverVehicleRepository extends JpaRepository<DriverVehicle, Long> {

}
