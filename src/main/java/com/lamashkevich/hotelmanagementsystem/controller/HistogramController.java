package com.lamashkevich.hotelmanagementsystem.controller;

import com.lamashkevich.hotelmanagementsystem.service.HistogramService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/property-view/histogram")
public class HistogramController {

    private final HistogramService histogramService;

    @GetMapping("/{param}")
    public Map<String, Long> getHistogram(@PathVariable String param) {
        return histogramService.getHistogram(param);
    }

}
