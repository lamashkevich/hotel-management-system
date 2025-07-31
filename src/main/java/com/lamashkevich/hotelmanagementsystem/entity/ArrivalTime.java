package com.lamashkevich.hotelmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "arrival_times")
public class ArrivalTime {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arrival_times_seq")
    @SequenceGenerator(name = "arrival_times_seq", sequenceName = "arrival_times_seq", allocationSize = 50)
    private Long id;
    private String checkIn;
    private String checkOut;

}
