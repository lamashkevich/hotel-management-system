package com.lamashkevich.hotelmanagementsystem.controller;

import com.lamashkevich.hotelmanagementsystem.dto.HotelFilter;
import com.lamashkevich.hotelmanagementsystem.dto.HotelPreviewResponse;
import com.lamashkevich.hotelmanagementsystem.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "Search hotels")
@RestController
@RequestMapping("/property-view/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @Operation(
            summary = "Search hotels by filter",
            description = "Returns a list of all hotels matching the filter with their brief information."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hotels found"),
            @ApiResponse(responseCode = "400", description = "Invalid filter")
    })
    @GetMapping
    public List<HotelPreviewResponse> search(@Valid HotelFilter hotelFilter) {
        return searchService.searchByFilter(hotelFilter);
    }

}
