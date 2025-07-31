package com.lamashkevich.hotelmanagementsystem.controller;

import com.lamashkevich.hotelmanagementsystem.exception.HistogramCreationException;
import com.lamashkevich.hotelmanagementsystem.service.HistogramService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HistogramController.class)
class HistogramControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HistogramService histogramService;

    @Test
    void getBrandHistogram_whenParamIsValid() throws Exception {
        var map = Map.of("Brand1", 1L, "Brand2", 2L, "Brand3", 3L);
        when(histogramService.getHistogram(anyString())).thenReturn(map);

        mockMvc.perform(get("/property-view/histogram/brand"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Brand1").value(1))
                .andExpect(jsonPath("$.Brand2").value(2))
                .andExpect(jsonPath("$.Brand3").value(3));
    }

    @Test
    void getHistogram_whenParamIsInvalid() throws Exception {
        var map = Map.of("Minsk", 1L, "Moskow", 2L, "Mogilev", 3L);
        when(histogramService.getHistogram(anyString())).thenReturn(map);

        when(histogramService.getHistogram(anyString()))
                .thenThrow(new HistogramCreationException("Invalid param"));

        mockMvc.perform(get("/property-view/histogram/invalid-param")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}