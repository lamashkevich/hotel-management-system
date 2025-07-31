package com.lamashkevich.hotelmanagementsystem.service;

import com.lamashkevich.hotelmanagementsystem.dto.HistogramItem;
import com.lamashkevich.hotelmanagementsystem.exception.HistogramCreationException;
import com.lamashkevich.hotelmanagementsystem.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HistogramServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HistogramService histogramService;

    @Test
    void getHistogram_whenParamIsBrand() {
        var param = "branD";
        var list = List.of(
                new HistogramItem("h1", 1L),
                new HistogramItem("h2", 2L)
        );

        var expectedMap = Map.of("h1", 1L, "h2", 2L);

        when(hotelRepository.countHotelsByBrand()).thenReturn(list);

        var result = histogramService.getHistogram(param);

        assertNotNull(result);
        assertEquals(result, expectedMap);
    }

    @Test
    void getHistogram_whenParamIsCity() {
        var param = "citY";
        var list = List.of(
                new HistogramItem("h1", 1L),
                new HistogramItem("h2", 2L)
        );

        var expectedMap = Map.of("h1", 1L, "h2", 2L);

        when(hotelRepository.countHotelsByCity()).thenReturn(list);

        var result = histogramService.getHistogram(param);

        assertNotNull(result);
        assertEquals(result, expectedMap);
    }

    @Test
    void getHistogram_whenParamIsCountry() {
        var param = "Country";
        var list = List.of(
                new HistogramItem("h1", 1L),
                new HistogramItem("h2", 2L)
        );

        var expectedMap = Map.of("h1", 1L, "h2", 2L);

        when(hotelRepository.countHotelsByCountry()).thenReturn(list);

        var result = histogramService.getHistogram(param);

        assertNotNull(result);
        assertEquals(result, expectedMap);
    }

    @Test
    void getHistogram_whenParamIsAmenities() {
        var param = "Amenities";
        var list = List.of(
                new HistogramItem("h1", 1L),
                new HistogramItem("h2", 2L)
        );

        var expectedMap = Map.of("h1", 1L, "h2", 2L);

        when(hotelRepository.countHotelsByAmenity()).thenReturn(list);

        var result = histogramService.getHistogram(param);

        assertNotNull(result);
        assertEquals(result, expectedMap);
    }

    @Test
    void getHistogram_whenParamIsInvalid() {
        var param = "INVALID";

        assertThrows(HistogramCreationException.class,
                () -> histogramService.getHistogram(param));

    }

}