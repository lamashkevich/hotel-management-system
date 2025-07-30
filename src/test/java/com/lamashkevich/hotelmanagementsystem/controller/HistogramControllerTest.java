package com.lamashkevich.hotelmanagementsystem.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HistogramController.class)
class HistogramControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCityHistogram() throws Exception {
        mockMvc.perform(get("/property-view/histogram/city"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Minsk").value(1))
                .andExpect(jsonPath("$.Moskow").value(2))
                .andExpect(jsonPath("$.Mogilev").value(3));
    }

    @Test
    void getBrandHistogram() throws Exception {
        mockMvc.perform(get("/property-view/histogram/brand"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Hilton").value(1))
                .andExpect(jsonPath("$.Brand2").value(2))
                .andExpect(jsonPath("$.Brand3").value(3));
    }

    @Test
    void getCountryHistogram() throws Exception {
        mockMvc.perform(get("/property-view/histogram/country"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Belarus").value(1))
                .andExpect(jsonPath("$.Poland").value(2))
                .andExpect(jsonPath("$.Spain").value(3));
    }

    @Test
    void getAmenitiesHistogram() throws Exception {
        mockMvc.perform(get("/property-view/histogram/amenities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['Free parking']").value(10))
                .andExpect(jsonPath("$.['Free WiFi']").value(20))
                .andExpect(jsonPath("$.['Non-smoking rooms']").value(30))
                .andExpect(jsonPath("$.['Fitness center']").value(40));
    }

}