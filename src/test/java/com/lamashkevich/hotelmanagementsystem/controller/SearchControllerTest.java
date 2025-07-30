package com.lamashkevich.hotelmanagementsystem.controller;

import com.lamashkevich.hotelmanagementsystem.dto.HotelPreviewResponse;
import com.lamashkevich.hotelmanagementsystem.service.SearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(SearchController.class)
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SearchService searchService;

    @Test
    void search() throws Exception {
        var hotel = new HotelPreviewResponse(
                1L, "Hotel1", "Hotel #1 in Minsk",
                "19 Pobediteley Avenue", "+375 17 309-80-00"
        );

        when(searchService.searchByFilter(any())).thenReturn(List.of(hotel));

        mockMvc.perform(get("/property-view/search?city=minsk&amenities=Free parking")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Hotel1"))
                .andExpect(jsonPath("$[0].description").value("Hotel #1 in Minsk"))
                .andExpect(jsonPath("$[0].address").value("19 Pobediteley Avenue"))
                .andExpect(jsonPath("$[0].phone").value("+375 17 309-80-00"));
    }
}