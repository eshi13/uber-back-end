package com.rider.service.services;

import com.rider.service.model.Rider;
import com.rider.service.model.RiderPreference;
import com.rider.service.model.enums.PreferredLanguage;
import com.rider.service.repository.RiderPreferenceRepository;
import com.rider.service.repository.RiderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RiderService {

    private final RiderRepository riderRepository;
    private final RiderPreferenceRepository riderPreferenceRepository;


    public Rider createRider(Rider riderInput) {

        // Validate the input
        if (riderInput == null || riderInput.getName() == null || riderInput.getName().isEmpty()) {
            throw new IllegalArgumentException("Rider name cannot be null or empty");
        }

        RiderPreference preference = new RiderPreference();
        preference.setRider(riderInput);
        preference.setLanguage(PreferredLanguage.ENGLISH);
        riderInput.setPreferences(preference);

        riderPreferenceRepository.save(preference);

        // Save the rider to the repository
        return riderRepository.save(riderInput);

    }

    public Optional<Rider> getRiderById(Long riderId) {
        return riderRepository.findById(riderId);
    }

    public Rider updateRider(Long riderId, Rider updated) {
        if(riderId == null || updated == null) {
            throw new IllegalArgumentException("Rider ID and updated rider cannot be null");
        }

        Rider rider = riderRepository.findById(riderId)
                .orElseThrow(() -> new RuntimeException("Rider not found with id: " + riderId));

        rider.setName(updated.getName());
        rider.setEmail(updated.getEmail());
        rider.setRating(updated.getRating());
        rider.setPreferences(updated.getPreferences());
        rider.setPhoneNumber(updated.getPhoneNumber());
        riderRepository.save(rider);
        return rider;

    }

    public RiderPreference updatePreferences(Long riderId, RiderPreference updated) {
        RiderPreference prefs = riderPreferenceRepository.findById(riderId)
                .orElseThrow(() -> new EntityNotFoundException("Preferences not found"));

        prefs.setLanguage(updated.getLanguage());
        prefs.setPaymentMethod(updated.getPaymentMethod());

        return riderPreferenceRepository.save(prefs);
    }
}
