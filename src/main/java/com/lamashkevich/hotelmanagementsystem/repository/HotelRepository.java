package com.lamashkevich.hotelmanagementsystem.repository;

import com.lamashkevich.hotelmanagementsystem.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {
}
