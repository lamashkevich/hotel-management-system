package com.lamashkevich.hotelmanagementsystem.service;

import com.lamashkevich.hotelmanagementsystem.dto.HotelFilter;
import com.lamashkevich.hotelmanagementsystem.dto.HotelPreviewResponse;
import com.lamashkevich.hotelmanagementsystem.entity.Hotel;
import com.lamashkevich.hotelmanagementsystem.mapper.HotelMapper;
import com.lamashkevich.hotelmanagementsystem.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelMapper hotelMapper;

    @InjectMocks
    private SearchService searchService;

    @Test
    void searchByFilter() {
        var filter = new HotelFilter("", "", "", "", List.of());
        var id = 1L;
        var hotel = Hotel.builder()
                .id(id)
                .name("DoubleTree by Hilton Minsk")
                .description("The DoubleTree by...")
                .build();

        var response = new HotelPreviewResponse(
                id,
                "DoubleTree by Hilton Minsk",
                "The DoubleTree by...",
                "9 Pobediteley Avenue, Minsk, 220004, Belarus ",
                "+375-17-123-45-67"
        );

        when(hotelMapper.entityToPreviewResponse(hotel)).thenReturn(response);
        when(hotelRepository.findAll(any(Specification.class))).thenReturn(List.of(hotel));

        var result = searchService.searchByFilter(filter);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(id, result.get(0).id());
    }
}