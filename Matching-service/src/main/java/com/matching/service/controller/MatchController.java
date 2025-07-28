package com.matching.service.controller;

import com.matching.service.dto.MatchAcceptRequest;
import com.matching.service.dto.MatchResponse;
import com.matching.service.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchingService matchingService;
    @PostMapping("/accept")
    public ResponseEntity<String> acceptMatch(@RequestBody MatchAcceptRequest request) {
        boolean valid = matchingService.acceptMatch(request);
        if (valid) return ResponseEntity.ok("Trip Created");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Match not valid or expired");
    }

    @PostMapping("/{riderId}")
    public ResponseEntity<MatchResponse> match(@PathVariable Long riderId) {
        MatchResponse response = matchingService.matchRider(riderId);
        return ResponseEntity.ok(response);
    }

}
