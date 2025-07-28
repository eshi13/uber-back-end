package com.location.service.services;

import com.location.service.dto.NearbyDriverResponse;
import com.location.service.dto.RiderLocationResponse;
import com.location.service.model.DriverLocationHistory;
import com.location.service.model.NearByDriver;
import com.location.service.repository.DriverLocationHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {

    private final RedisTemplate<String, RiderLocationResponse> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private final DriverLocationHistoryRepository driverLocationHistoryRepository;
    private static final String GEO_KEY = "driver_locations";
    private static final String GEO_RIDER_KEY = "rider:locations:";

    public void updateDriverLocation(Long id, double latitude, double longitude) {

        //save in cache
        stringRedisTemplate.opsForGeo().add(GEO_KEY, new Point(latitude, longitude), id.toString());

        //save in database
        DriverLocationHistory history = new DriverLocationHistory();
        history.setDriverId(id);
        history.setLatitude(latitude);
        history.setLongitude(longitude);
        history.setTimestamp(Instant.now());

        driverLocationHistoryRepository.save(history);

        // Redis: Heartbeat key with TTL
        stringRedisTemplate.opsForValue()
                .set("driver:lastSeen:" + id, "1", 30, TimeUnit.SECONDS);
    }

    public void saveRiderLocation(Long riderId, double lat, double lng) {
        String key = GEO_RIDER_KEY + riderId;
        RiderLocationResponse response = new RiderLocationResponse(riderId, lat, lng);
        redisTemplate.opsForValue().set(key, response);
    }

    public List<NearbyDriverResponse> findNearByDrivers(double lat, double lng, double radiusInKm) {
        log.info("Finding nearby drivers for lat: {}, lng: {}, radius: {} km", lat, lng, radiusInKm);
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs
                .newGeoRadiusArgs()
                .includeDistance()
                .sortAscending()
                .limit(10);

        GeoResults<RedisGeoCommands.GeoLocation<String>> results = stringRedisTemplate
                .opsForGeo()
                .radius(GEO_KEY,
                        new Circle(new Point(lng, lat), new Distance(radiusInKm, Metrics.KILOMETERS)),
                        args);

        if (results == null || results.getContent().isEmpty()) {
            return Collections.emptyList();
        }

        for (GeoResult<RedisGeoCommands.GeoLocation<String>> r : results.getContent()) {
            log.info("Redis returned driver: {}", r.getContent().getName());
        }

        return results.getContent().stream()
//                .filter(r -> {
//                    String key = "driver:lastSeen:" + r.getContent().getName();
//                    Boolean exists = stringRedisTemplate.hasKey(key);
//                    log.info("Checking key '{}': {}", key, exists);
//                    return Boolean.TRUE.equals(exists);
//                })
                .map(r -> new NearbyDriverResponse(
                        Long.parseLong(r.getContent().getName()),
                        r.getDistance().getValue()
                ))
                .collect(Collectors.toList());
    }

    public RiderLocationResponse getRiderLocation(Long riderId) {
        // Implementation to fetch rider location
        // This method should be implemented based on your requirements
        String key = "rider:locations:" + riderId;
        log.info("Fetching rider location for riderId: {}", riderId);
        RiderLocationResponse value = redisTemplate.opsForValue().get(key);

        if (value != null) {
            return value;
        }
        return null;
    }
}
