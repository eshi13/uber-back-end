package com.matching.service.clients;

import com.matching.service.dto.NearbyDriverResponse;
import com.matching.service.dto.RiderLocationResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface LocationServiceClient {

    @GetExchange("/api/location/rider/{riderId}")
    RiderLocationResponse getRiderLocation(@PathVariable Long riderId);

    @GetExchange("/api/location/nearby")
    List<NearbyDriverResponse> findNearByDrivers(@RequestParam double lat,
                                                 @RequestParam double lng,
                                                 @RequestParam double radiusInKm);
}
