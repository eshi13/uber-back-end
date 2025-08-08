package com.notification.service.client;

import com.notification.service.dto.CreateTripRequest;
import com.notification.service.dto.CreateTripResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface TripServiceClient {

    @PostExchange("/api/trips/create")
    ResponseEntity<CreateTripResponse> createTrip(@RequestBody CreateTripRequest request);

}

