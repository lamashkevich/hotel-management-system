package com.lamashkevich.hotelmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hotel_amenities")
public class HotelAmenity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_amenity_seq")
    @SequenceGenerator(name = "hotel_amenity_seq", sequenceName = "hotel_amenity_seq", allocationSize = 50)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HotelAmenity)) return false;
        HotelAmenity that = (HotelAmenity) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
