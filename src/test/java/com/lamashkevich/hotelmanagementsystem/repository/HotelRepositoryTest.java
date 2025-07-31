package com.lamashkevich.hotelmanagementsystem.repository;

import com.lamashkevich.hotelmanagementsystem.dto.HistogramItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
@ActiveProfiles("test")
@Sql(scripts = "/init-for-histogram.sql")
class HotelRepositoryTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    void countHotelsByBrand() {
        var expextedList = List.of(
          new HistogramItem("brand1", 3L),
          new HistogramItem("brand2", 2L),
          new HistogramItem("brand3", 1L)
        );

        var result = hotelRepository.countHotelsByBrand();

        var resultInLowerCase = itemsToLowerCase(result);

        assertNotNull(result);
        assertEquals(expextedList.size(), result.size());
        assertTrue(resultInLowerCase.containsAll(expextedList));
        assertTrue(expextedList.containsAll(resultInLowerCase));
    }

    @Test
    void countHotelsByCity() {
        var expextedList = List.of(
                new HistogramItem("minsk", 3L),
                new HistogramItem("grodno", 2L),
                new HistogramItem("brest", 1L)
        );

        var result = hotelRepository.countHotelsByCity();

        var resultInLowerCase = itemsToLowerCase(result);

        assertNotNull(result);
        assertEquals(expextedList.size(), result.size());
        assertTrue(resultInLowerCase.containsAll(expextedList));
        assertTrue(expextedList.containsAll(resultInLowerCase));
    }

    @Test
    void countHotelsByCountry() {
        var expextedList = List.of(
                new HistogramItem("belarus", 4L),
                new HistogramItem("blr", 2L)
        );

        var result = hotelRepository.countHotelsByCountry();

        var resultInLowerCase = itemsToLowerCase(result);

        assertNotNull(result);
        assertEquals(expextedList.size(), result.size());
        assertTrue(resultInLowerCase.containsAll(expextedList));
        assertTrue(expextedList.containsAll(resultInLowerCase));
    }


    @Test
    void countHotelsByAmenity() {
        var expextedList = List.of(
                new HistogramItem("non-smoking rooms", 4L),
                new HistogramItem("fitness center", 1L),
                new HistogramItem("free wifi", 3L),
                new HistogramItem("on-site restaurant", 1L),
                new HistogramItem("concierge", 2L),
                new HistogramItem("free parking", 1L)
        );

        var result = hotelRepository.countHotelsByAmenity();

        var resultInLowerCase = itemsToLowerCase(result);

        assertNotNull(result);
        assertEquals(expextedList.size(), result.size());
        assertTrue(resultInLowerCase.containsAll(expextedList));
        assertTrue(expextedList.containsAll(resultInLowerCase));
    }

    private List<HistogramItem> itemsToLowerCase(List<HistogramItem> items) {
        return items.stream()
                .map(i -> new HistogramItem(i.value().toLowerCase(), i.count()))
                .toList();
    }
}