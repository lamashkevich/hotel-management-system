package com.lamashkevich.hotelmanagementsystem.repository;

import com.lamashkevich.hotelmanagementsystem.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
