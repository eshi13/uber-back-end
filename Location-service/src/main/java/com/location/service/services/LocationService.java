package com.location.service.services;

import com.location.service.model.DriverLocationHistory;
import com.location.service.model.NearByDriver;
import com.location.service.repository.DriverLocationHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final StringRedisTemplate redisTemplate;
    private final DriverLocationHistoryRepository driverLocationHistoryRepository;
    private static final String GEO_KEY = "driver_locations";

    public void updateDriverLocation(Long id, double latitude, double longitude) {

        //save in cache
        redisTemplate.opsForGeo().add(GEO_KEY, new Point(latitude, longitude), id.toString());

        //save in database
        DriverLocationHistory history = new DriverLocationHistory();
        history.setDriverId(id);
        history.setLatitude(latitude);
        history.setLongitude(longitude);
        history.setTimestamp(Instant.now());

        driverLocationHistoryRepository.save(history);

        // Redis: Heartbeat key with TTL
        redisTemplate.opsForValue()
                .set("driver:lastSeen:" + id, "1", 30, TimeUnit.SECONDS);
    }

    public List<NearByDriver> findNearByDrivers(double lat, double lng, double radiusInKm) {
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs
                .newGeoRadiusArgs()
                .includeDistance()
                .sortAscending()
                .limit(10);

        GeoResults<RedisGeoCommands.GeoLocation<String>> results = redisTemplate
                .opsForGeo()
                .radius(GEO_KEY,
                        new Circle(new Point(lng, lat), new Distance(radiusInKm, Metrics.KILOMETERS)),
                        args);

        if (results == null || results.getContent().isEmpty()) {
            return Collections.emptyList();
        }

        return results.getContent().stream()
                .filter(r -> {
                    String key = "driver:lastSeen:" + r.getContent().getName();
                    return Boolean.TRUE.equals(redisTemplate.hasKey(key));
                })
                .map(r -> new NearByDriver(
                        Long.parseLong(r.getContent().getName()),
                        r.getDistance().getValue()
                ))
                .collect(Collectors.toList());
    }
}
