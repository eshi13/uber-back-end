package com.location.service.repository;

import com.location.service.model.DriverLocationHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DriverLocationHistoryRepository extends MongoRepository<DriverLocationHistory, String> {

}
