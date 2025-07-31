package com.lamashkevich.hotelmanagementsystem.repository;

import com.lamashkevich.hotelmanagementsystem.dto.HistogramItem;
import com.lamashkevich.hotelmanagementsystem.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {

    @Query("SELECT new com.lamashkevich.hotelmanagementsystem.dto.HistogramItem(" +
            "MIN(h.brand), COUNT(h)) " +
            "FROM Hotel h " +
            "WHERE h.brand IS NOT NULL " +
            "GROUP BY UPPER(h.brand)")
    List<HistogramItem> countHotelsByBrand();

    @Query("SELECT new com.lamashkevich.hotelmanagementsystem.dto.HistogramItem(" +
            "MIN(h.address.city), COUNT(h)) " +
            "FROM Hotel h " +
            "WHERE h.address.city IS NOT NULL " +
            "GROUP BY UPPER(h.address.city)")
    List<HistogramItem> countHotelsByCity();

    @Query("SELECT new com.lamashkevich.hotelmanagementsystem.dto.HistogramItem(" +
            "MIN(h.address.country), COUNT(h)) " +
            "FROM Hotel h " +
            "WHERE h.address.country IS NOT NULL " +
            "GROUP BY UPPER(h.address.country)")
    List<HistogramItem> countHotelsByCountry();


    @Query("SELECT new com.lamashkevich.hotelmanagementsystem.dto.HistogramItem(" +
            "MIN(a.name), COUNT(h)) " +
            "FROM HotelAmenity a " +
            "JOIN a.hotel h " +
            "GROUP BY UPPER(a.name)")
    List<HistogramItem> countHotelsByAmenity();
}
