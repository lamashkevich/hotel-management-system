package com.lamashkevich.hotelmanagementsystem.controller;


import com.lamashkevich.hotelmanagementsystem.dto.HotelPreviewResponse;
import com.lamashkevich.hotelmanagementsystem.dto.HotelRequest;
import com.lamashkevich.hotelmanagementsystem.dto.HotelResponse;
import com.lamashkevich.hotelmanagementsystem.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Tag(name = "Hotel management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/property-view/hotels")
public class HotelController {

    private final HotelService hotelService;

    @Operation(
            summary = "Get all hotels",
            description = "Returns a list of all the hotels with their brief information"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hotels found"),
    })
    @GetMapping
    public List<HotelPreviewResponse> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @Operation(
            summary = "Get hotel by Id",
            description = "Returns detailed information about hotel by its Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hotel found"),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @GetMapping("/{id}")
    public HotelResponse getHotel(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @Operation(
            summary = "Create new hotel",
            description = "Creates a new hotel, returns the created hotel preview"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Hotel is created"),
            @ApiResponse(responseCode = "400", description = "Hotel is not created")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HotelPreviewResponse addHotel(@RequestBody @Valid HotelRequest request) {
        return hotelService.addHotel(request);
    }

    @Operation(
            summary = "Add amenities to hotel",
            description = "Adds a set of amenities to hotel by Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Amenities added"),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @PostMapping("{id}/amenities")
    public void addAmenities(@PathVariable Long id, @RequestBody Set<String> amenities) {
        hotelService.addAmenities(id, amenities);
    }


}
