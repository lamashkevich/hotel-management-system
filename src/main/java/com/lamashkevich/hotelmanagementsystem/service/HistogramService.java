package com.lamashkevich.hotelmanagementsystem.service;

import com.lamashkevich.hotelmanagementsystem.dto.HistogramItem;
import com.lamashkevich.hotelmanagementsystem.exception.HistogramCreationException;
import com.lamashkevich.hotelmanagementsystem.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistogramService {

    private final HotelRepository hotelRepository;

    public Map<String, Long> getHistogram(String param) {
        log.info("Getting histogram for {}", param);

        List<HistogramItem> listHistogramItems = switch (param.toLowerCase()) {
            case "brand" -> hotelRepository.countHotelsByBrand();
            case "city" -> hotelRepository.countHotelsByCity();
            case "country" -> hotelRepository.countHotelsByCountry();
            case "amenities" -> hotelRepository.countHotelsByAmenity();
            default -> throw new HistogramCreationException("Invalid param");
        };

        return convertToMap(listHistogramItems);
    }

    private Map<String, Long> convertToMap(List<HistogramItem> histogramItems) {
        return histogramItems.stream()
                .collect(Collectors.toMap(HistogramItem::value, HistogramItem::count));
    }

}
