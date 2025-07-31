package com.lamashkevich.hotelmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotels_seq")
    @SequenceGenerator(name = "hotels_seq", sequenceName = "hotels_seq", allocationSize = 50)
    private Long id;

    private String name;

    private String description;

    private String brand;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    private Contacts contacts;

    @OneToOne(cascade = CascadeType.ALL)
    private ArrivalTime arrivalTime;

    @BatchSize(size = 50)
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HotelAmenity> amenities;
}
