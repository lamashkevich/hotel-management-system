package com.lamashkevich.hotelmanagementsystem.service;

import com.lamashkevich.hotelmanagementsystem.dto.HotelPreviewResponse;
import com.lamashkevich.hotelmanagementsystem.dto.HotelRequest;
import com.lamashkevich.hotelmanagementsystem.dto.HotelResponse;
import com.lamashkevich.hotelmanagementsystem.entity.HotelAmenity;
import com.lamashkevich.hotelmanagementsystem.exception.HotelCreationException;
import com.lamashkevich.hotelmanagementsystem.exception.HotelNotFoundException;
import com.lamashkevich.hotelmanagementsystem.mapper.HotelMapper;
import com.lamashkevich.hotelmanagementsystem.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public List<HotelPreviewResponse> getAllHotels() {
        log.info("Getting all hotels");
        return hotelRepository.findAll()
                .stream()
                .map(hotelMapper::entityToPreviewResponse)
                .toList();
    }

    public HotelResponse getHotelById(Long id) {
        log.info("Getting hotel by id: {}", id);
        return hotelRepository.findById(id)
                .map(hotelMapper::entityToResponse)
                .orElseThrow(() -> new HotelNotFoundException(id));
    }

    @Transactional
    public HotelPreviewResponse addHotel(HotelRequest hotelRequest) {
        log.info("Adding hotel {}", hotelRequest.name());
        return Optional.of(hotelRequest)
                .map(hotelMapper::requestToEntity)
                .map(hotelRepository::save)
                .map(hotelMapper::entityToPreviewResponse)
                .orElseThrow(HotelCreationException::new);
    }

    @Transactional
    public void addAmenities(Long hotelId, Set<String> amenities) {
        log.info("Adding amenities to hotel {}", hotelId);
        var hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException(hotelId));

        var newAmenities = amenities.stream()
                .map(amenity ->
                        HotelAmenity.builder()
                                .name(amenity)
                                .hotel(hotel)
                                .build())
                .collect(Collectors.toSet());

        hotel.getAmenities().addAll(newAmenities);
    }
}
