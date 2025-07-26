package com.rider.service.repository;

import com.rider.service.model.RiderPreference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiderPreferenceRepository extends JpaRepository<RiderPreference, Long> {
}
