package com.lamashkevich.hotelmanagementsystem.repository.spec;

import com.lamashkevich.hotelmanagementsystem.dto.HotelFilter;
import com.lamashkevich.hotelmanagementsystem.repository.HotelRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
@Transactional
@ActiveProfiles("test")
@Sql(scripts = "/init.sql")
class HotelSpecificationTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    void filter_whenHotelFilterIsEmpty() {
        var filter = new HotelFilter(null, null, null, null, null);
        var result = hotelRepository.findAll(HotelSpecification.filter(filter));

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @ParameterizedTest
    @CsvSource({
            "Hotel #1, 1",
            "hotel #2, 1",
            "Hotel #3, 1",
            "hotel, 3",
            "Not exist, 0"
    })
    void hasName(String name, Integer expectedSize) {
        var result = hotelRepository.findAll(HotelSpecification.hasName(name));

        assertEquals(expectedSize, result.size());
        if (expectedSize > 0) {
            result.forEach(hotel -> assertTrue(
                    hotel.getName().toLowerCase().contains(name.toLowerCase())));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "brand1, 1",
            "Brand2, 1",
            "brand3, 1",
            "Brand, 3",
            "Not exist, 0"
    })
    void hasBrand(String brand, Integer expectedSize) {
        var result = hotelRepository.findAll(HotelSpecification.hasBrand(brand));

        assertEquals(expectedSize, result.size());
        if (expectedSize > 0) {
            result.forEach(hotel -> assertTrue(
                    hotel.getBrand().toLowerCase().contains(brand.toLowerCase())));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "Minsk, 1",
            "Grodno, 1",
            "Brest, 1",
            "r, 2",
            "Not exist, 0"
    })
    void hasCity(String city, Integer expectedSize) {
        var result = hotelRepository.findAll(HotelSpecification.hasCity(city));

        assertEquals(expectedSize, result.size());
        if (expectedSize > 0) {
            result.forEach(hotel -> assertTrue(
                    hotel.getAddress().getCity().toLowerCase().contains(city.toLowerCase())));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "Belarus, 2",
            "BELARUS, 2",
            "BLR, 1",
            "Not exist, 0"
    })
    void hasCountry(String country, Integer expectedSize) {
        var result = hotelRepository.findAll(HotelSpecification.hasCountry(country));

        assertEquals(expectedSize, result.size());
        if (expectedSize > 0) {
            result.forEach(hotel -> assertTrue(
                    hotel.getAddress().getCountry().toLowerCase().contains(country.toLowerCase())));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "Free parking, Free WiFi, 1",
            "free wifi, , 3",
            "Concierge, , 2",
            "Fitness center, , 1",
            "Not exist, , 0"
    })
    void hasAmenities(String amenity1, String amenity2, Integer expectedSize) {
        var amenities = Stream.of(amenity1, amenity2)
                .filter(Objects::nonNull)
                .toList();

        var result = hotelRepository.findAll(HotelSpecification.hasAmenities(amenities));

        assertEquals(expectedSize, result.size());
        if (expectedSize > 0) {
            var amenitiesInLowerCase = amenities.stream()
                    .map(String::toLowerCase)
                    .toList();

            result.forEach(hotel -> {

                Set<String> amenityNames = hotel.getAmenities()
                        .stream()
                        .map(amenity -> amenity.getName().toLowerCase())
                        .collect(Collectors.toSet());
                assertTrue(amenityNames.containsAll(amenitiesInLowerCase));
            });
        }
    }
}