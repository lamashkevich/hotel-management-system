package com.lamashkevich.hotelmanagementsystem.controller;

import com.lamashkevich.hotelmanagementsystem.dto.HotelFilter;
import com.lamashkevich.hotelmanagementsystem.dto.HotelPreviewResponse;
import com.lamashkevich.hotelmanagementsystem.service.SearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/property-view/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public List<HotelPreviewResponse> search(@Valid HotelFilter hotelFilter) {
        return searchService.searchByFilter(hotelFilter);
    }

}
