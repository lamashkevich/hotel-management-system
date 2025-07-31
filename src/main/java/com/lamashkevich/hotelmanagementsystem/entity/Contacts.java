package com.lamashkevich.hotelmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contacts")
public class Contacts {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contacts_seq")
    @SequenceGenerator(name = "contacts_seq", sequenceName = "contacts_seq", allocationSize = 50)
    private Long id;
    private String phone;
    private String email;
}
