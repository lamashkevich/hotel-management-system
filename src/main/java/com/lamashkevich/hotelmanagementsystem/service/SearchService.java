package com.lamashkevich.hotelmanagementsystem.service;

import com.lamashkevich.hotelmanagementsystem.dto.HotelFilter;
import com.lamashkevich.hotelmanagementsystem.dto.HotelPreviewResponse;
import com.lamashkevich.hotelmanagementsystem.mapper.HotelMapper;
import com.lamashkevich.hotelmanagementsystem.repository.HotelRepository;
import com.lamashkevich.hotelmanagementsystem.repository.spec.HotelSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public List<HotelPreviewResponse> searchByFilter(HotelFilter filter) {
        log.info("Searching hotels with filter: {}", filter);
        return hotelRepository
                .findAll(HotelSpecification.filter(filter))
                .stream()
                .map(hotelMapper::entityToPreviewResponse)
                .toList();
    }

}
