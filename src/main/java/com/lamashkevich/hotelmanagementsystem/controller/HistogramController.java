package com.lamashkevich.hotelmanagementsystem.controller;

import com.lamashkevich.hotelmanagementsystem.service.HistogramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "Display a histogram")
@RestController
@RequiredArgsConstructor
@RequestMapping("/property-view/histogram")
public class HistogramController {

    private final HistogramService histogramService;

    @Operation(
            summary = "Get histogram for a given parameter",
            description = "Supported parameters: 'brand', 'city', 'country', 'amenities'"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hotels found"),
            @ApiResponse(responseCode = "400", description = "Invalid param")
    })
    @GetMapping("/{param}")
    public Map<String, Long> getHistogram(@PathVariable String param) {
        return histogramService.getHistogram(param);
    }

}
