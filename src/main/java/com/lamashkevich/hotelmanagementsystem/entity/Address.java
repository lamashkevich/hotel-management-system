package com.lamashkevich.hotelmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addresses_seq")
    @SequenceGenerator(name = "addresses_seq", sequenceName = "addresses_seq", allocationSize = 50)
    private Long id;
    private Integer houseNumber;
    private String street;
    private String city;
    private String country;
    private String postCode;

}
