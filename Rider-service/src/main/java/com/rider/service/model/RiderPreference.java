package com.rider.service.model;

import com.rider.service.model.enums.PreferredLanguage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "rider_preference")
public class RiderPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "rider_id")
    private Rider rider;

    @Enumerated(EnumType.STRING)
    private PreferredLanguage language = PreferredLanguage.ENGLISH;
    private String paymentMethod;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
