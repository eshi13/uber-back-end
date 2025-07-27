package com.matching.service.repository;

import com.matching.service.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByDriverId(Long driverId);
    List<Trip> findByRiderId(Long riderId);
}
