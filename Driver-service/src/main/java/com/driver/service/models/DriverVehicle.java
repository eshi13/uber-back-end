package com.driver.service.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "driver_vehicle")
public class DriverVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    private String vehicleMake;

    private String vehicleModel;

    private String vehicleNumber;

    private String vehicleColor;

    private String vehicleType;

    @CreationTimestamp
    private LocalDateTime registrationDate;


}
