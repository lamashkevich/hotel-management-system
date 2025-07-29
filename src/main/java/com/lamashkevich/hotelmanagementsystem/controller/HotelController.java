package com.lamashkevich.hotelmanagementsystem.controller;


import com.lamashkevich.hotelmanagementsystem.dto.HotelPreviewResponse;
import com.lamashkevich.hotelmanagementsystem.dto.HotelRequest;
import com.lamashkevich.hotelmanagementsystem.dto.HotelResponse;
import com.lamashkevich.hotelmanagementsystem.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequiredArgsConstructor
@RequestMapping("/property-view/hotels")
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    public List<HotelPreviewResponse> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/{id}")
    public HotelResponse getHotel(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HotelPreviewResponse addHotel(@RequestBody @Valid HotelRequest request) {
        return hotelService.addHotel(request);
    }

    @PostMapping("{id}/amenities")
    public void addAmenities(@PathVariable Long id, @RequestBody Set<String> amenities) {
        hotelService.addAmenities(id, amenities);
    }


}
