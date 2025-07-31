package com.lamashkevich.hotelmanagementsystem.service;

import com.lamashkevich.hotelmanagementsystem.dto.AddressRequest;
import com.lamashkevich.hotelmanagementsystem.dto.ArrivalTimeRequest;
import com.lamashkevich.hotelmanagementsystem.dto.ContactsRequest;
import com.lamashkevich.hotelmanagementsystem.dto.HotelRequest;
import com.lamashkevich.hotelmanagementsystem.entity.HotelAmenity;
import com.lamashkevich.hotelmanagementsystem.exception.HotelCreationException;
import com.lamashkevich.hotelmanagementsystem.exception.HotelNotFoundException;
import com.lamashkevich.hotelmanagementsystem.mapper.HotelMapper;
import com.lamashkevich.hotelmanagementsystem.repository.HotelRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class HotelServiceTest {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    @Sql(scripts = "/init.sql")
    void getAllHotels_whenHotelsAreFound() {
        var result = hotelService.getAllHotels();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertFalse(result.get(0).address().isEmpty());
        assertFalse(result.get(0).name().isEmpty());
        assertFalse(result.get(0).description().isEmpty());
        assertFalse(result.get(0).phone().isEmpty());
    }


    @Test
    void getAllHotels_whenHotelsAreNotFound() {
        var result = hotelService.getAllHotels();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @Sql(scripts = "/init.sql")
    void getHotelById_whenHotelIsFound() {
        var id = 1L;
        var result = hotelService.getHotelById(id);

        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals("Hotel #1", result.name());
        assertEquals("Hotel #1 in Belarus", result.description());
        assertEquals("Brand1", result.brand());
        assertEquals(10, result.address().houseNumber());
        assertEquals("Street1", result.address().street());
        assertEquals("Minsk", result.address().city());
        assertEquals("Belarus", result.address().country());
        assertEquals("100000", result.address().postCode());
        assertEquals("11:00", result.arrivalTime().checkIn());
        assertEquals("10:10", result.arrivalTime().checkOut());
        assertEquals("+375-17-123-45-67", result.contacts().phone());
        assertEquals("info@hotel1.com", result.contacts().email());
        assertTrue(result.amenities().containsAll(List.of("Free WiFi", "Free parking", "Concierge")));
    }

    @Test
    void getHotelById_whenHotelIsNotFound() {
        var id = 999L;

        assertThrows(HotelNotFoundException.class, () -> hotelService.getHotelById(id));
    }

    @Test
    void addHotel() {
        var request = new HotelRequest(
                "DoubleTree by Hilton Minsk", "The DoubleTree by...", "Hilton",
                new AddressRequest(9, "Pobediteley Avenue", "Minsk", "Belarus", "220004"),
                new ContactsRequest("+375 17 309-80-00", "doubletreeminsk.info@hilton.com"),
                new ArrivalTimeRequest("14:00", "12:00")
        );

        var result = hotelService.addHotel(request);

        assertNotNull(result);
        assertEquals("DoubleTree by Hilton Minsk", result.name());
        assertEquals("The DoubleTree by...", result.description());
        assertEquals("+375 17 309-80-00", result.phone());
        assertEquals("9 Pobediteley Avenue, Minsk, 220004, Belarus", result.address());
    }

    @Test
    void addHotel_thenThrowsHotelCreationException() {
        var request = new HotelRequest(
                "DoubleTree by Hilton Minsk", "The DoubleTree by...", "Hilton",
                new AddressRequest(9, "Pobediteley Avenue", "Minsk", "Belarus", "220004"),
                new ContactsRequest("+375 17 309-80-00", "doubletreeminsk.info@hilton.com"),
                new ArrivalTimeRequest("14:00", "12:00")
        );

        var originalMapper = (HotelMapper) ReflectionTestUtils.getField(hotelService, "hotelMapper");
        try {
            var mockMapper = mock(HotelMapper.class);
            when(mockMapper.entityToPreviewResponse(any())).thenThrow(RuntimeException.class);
            ReflectionTestUtils.setField(hotelService, "hotelMapper", mockMapper);

            assertThrows(HotelCreationException.class, () -> hotelService.addHotel(request));
        } finally {
            ReflectionTestUtils.setField(hotelService, "hotelMapper", originalMapper);
        }

    }

    @Test
    @Sql(scripts = "/init.sql")
    void addAmenities_whenHotelIsFound() {
        var id = 1L;

        var amenities = Set.of("Free parking", "Meeting rooms");
        hotelService.addAmenities(id, amenities);

        var hotel = hotelRepository.findById(id).orElseThrow();
        var foundAmenities = hotel.getAmenities().stream()
                .map(HotelAmenity::getName)
                .collect(Collectors.toSet());
        assertTrue(foundAmenities.containsAll(amenities));
    }

    @Test
    void addAmenities_whenHotelIsNotFound() {
        var id = 999L;
        var amenities = Set.of("Business center", "Meeting rooms");

        assertThrows(HotelNotFoundException.class, () -> hotelService.addAmenities(id, amenities));
    }
}