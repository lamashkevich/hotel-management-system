package com.lamashkevich.hotelmanagementsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/property-view//histogram")
@RestController
public class HistogramController {

    @GetMapping("/{param}")
    public Map<String, Long> getHistogram(@PathVariable String param) {
        return null;
    }

}
