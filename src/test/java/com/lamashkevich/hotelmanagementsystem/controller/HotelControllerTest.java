package com.lamashkevich.hotelmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamashkevich.hotelmanagementsystem.dto.*;
import com.lamashkevich.hotelmanagementsystem.exception.HotelCreationException;
import com.lamashkevich.hotelmanagementsystem.exception.HotelNotFoundException;
import com.lamashkevich.hotelmanagementsystem.service.HotelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HotelController.class)
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private HotelService hotelService;

    @Test
    void getAll() throws Exception {
        var hotel1 = new HotelPreviewResponse(
                1L, "Hotel1", "Hotel #1 in Minsk",
                "19 Pobediteley Avenue", "+375 17 309-80-00"
        );
        var hotel2 = new HotelPreviewResponse(
                2L, "Hotel2", "Hotel #2 in Minsk",
                "29 Pobediteley Avenue", "+375 29 309-80-00"
        );

        when(hotelService.getAllHotels()).thenReturn(List.of(hotel1, hotel2));

        mockMvc.perform(get("/property-view/hotels")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[0].name").value("Hotel1"))
                .andExpect(jsonPath("$[1].name").value("Hotel2"))
                .andExpect(jsonPath("$[0].description").value("Hotel #1 in Minsk"))
                .andExpect(jsonPath("$[1].description").value("Hotel #2 in Minsk"))
                .andExpect(jsonPath("$[0].address").value("19 Pobediteley Avenue"))
                .andExpect(jsonPath("$[1].address").value("29 Pobediteley Avenue"))
                .andExpect(jsonPath("$[0].phone").value("+375 17 309-80-00"))
                .andExpect(jsonPath("$[1].phone").value("+375 29 309-80-00"));
    }

    @Test
    void getById_whenHotelIsFound() throws Exception {
        var id = 1L;
        var hotel = new HotelResponse(
                id,
                "DoubleTree by Hilton Minsk", "The DoubleTree by...", "Hilton",
                new AddressResponse(9, "Pobediteley Avenue", "Minsk", "Belarus", "220004"),
                new ContactsResponse("+375 17 309-80-00", "doubletreeminsk.info@hilton.com"),
                new ArrivalTimeResponse("14:00", "12:00"),
                Set.of("Free parking", "Free WiFi", "Non-smoking rooms")
        );

        when(hotelService.getHotelById(id)).thenReturn(hotel);

        mockMvc.perform(get("/property-view/hotels/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("DoubleTree by Hilton Minsk"))
                .andExpect(jsonPath("$.description").value("The DoubleTree by..."))
                .andExpect(jsonPath("$.brand").value("Hilton"))
                .andExpect(jsonPath("$.address.houseNumber").value("9"))
                .andExpect(jsonPath("$.address.street").value("Pobediteley Avenue"))
                .andExpect(jsonPath("$.address.city").value("Minsk"))
                .andExpect(jsonPath("$.address.country").value("Belarus"))
                .andExpect(jsonPath("$.address.postCode").value("220004"))
                .andExpect(jsonPath("$.contacts.phone").value("+375 17 309-80-00"))
                .andExpect(jsonPath("$.contacts.email").value("doubletreeminsk.info@hilton.com"))
                .andExpect(jsonPath("$.arrivalTime.checkIn").value("14:00"))
                .andExpect(jsonPath("$.arrivalTime.checkOut").value("12:00"))
                .andExpect(jsonPath("$.amenities")
                        .value(hasItems("Free parking", "Free WiFi", "Non-smoking rooms")));
    }

    @Test
    void getById_whenHotelIsNotFound() throws Exception {
        var id = 999L;

        when(hotelService.getHotelById(id)).thenThrow(new HotelNotFoundException(id));

        mockMvc.perform(get("/property-view/hotels/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void addHotel_whenRequestIsValid() throws Exception {
        var request = new HotelRequest(
                "DoubleTree by Hilton Minsk",
                "The DoubleTree by...",
                "Hilton",
                new AddressRequest(9, "Pobediteley Avenue", "Minsk", "Belarus", "220004"),
                new ContactsRequest("+375 17 309-80-00", "doubletreeminsk.info@hilton.com"),
                new ArrivalTimeRequest("14:00", "12:00")
        );

        var hotel = new HotelPreviewResponse(
                1L, "DoubleTree by Hilton Minsk", "The DoubleTree by...",
                "9 Pobediteley Avenue", "+375 17 309-80-00"
        );

        when(hotelService.addHotel(any(HotelRequest.class))).thenReturn(hotel);

        mockMvc.perform(post("/property-view/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("DoubleTree by Hilton Minsk"))
                .andExpect(jsonPath("$.description").value("The DoubleTree by..."))
                .andExpect(jsonPath("$.address").value("9 Pobediteley Avenue"))
                .andExpect(jsonPath("$.phone").value("+375 17 309-80-00"));
    }

    @Test
    void addHotel_whenHotelNotSaved() throws Exception {
        var request = new HotelRequest(
                "DoubleTree by Hilton Minsk",
                "The DoubleTree by...",
                "Hilton",
                new AddressRequest(9, "Pobediteley Avenue", "Minsk", "Belarus", "220004"),
                new ContactsRequest("+375 17 309-80-00", "doubletreeminsk.info@hilton.com"),
                new ArrivalTimeRequest("14:00", "12:00")
        );

        when(hotelService.addHotel(any(HotelRequest.class))).thenThrow(new HotelCreationException());

        mockMvc.perform(post("/property-view/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void addAmenities_whenHotelIsFound() throws Exception {
        var id = 1L;
        var request = List.of("Free parking", "Free WiFi", "Non-smoking rooms");

        mockMvc.perform(post("/property-view/hotels/" + id + "/amenities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addAmenities_whenHotelIsNotFound() throws Exception {
        var id = 999L;
        var request = Set.of("Free parking", "Free WiFi", "Non-smoking rooms");

        doThrow(new HotelNotFoundException(id)).when(hotelService).addAmenities(id, request);

        mockMvc.perform(post("/property-view/hotels/" + id + "/amenities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void addHotel_whenHotelRequestIsInvalid() throws Exception {
        var request = new HotelRequest("", null, null, null, null, null);

        mockMvc.perform(post("/property-view/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").doesNotExist())
                .andExpect(jsonPath("$.name").value("Name is required"))
                .andExpect(jsonPath("$.brand").value("Brand is required"))
                .andExpect(jsonPath("$.address").value("Address is required"))
                .andExpect(jsonPath("$.contacts").value("Contacts is required"))
                .andExpect(jsonPath("$.arrivalTime").value("Arrival time is required"));
    }

    @Test
    void addHotel_whenAddressRequestIsInvalid() throws Exception {
        var request = new HotelRequest(
                "DoubleTree by Hilton Minsk",
                "The DoubleTree by...",
                "Hilton",
                new AddressRequest(null, "", "", null, ""),
                new ContactsRequest("+375 17 309-80-00", "doubletreeminsk.info@hilton.com"),
                new ArrivalTimeRequest("14:00", "12:00")
        );

        mockMvc.perform(post("/property-view/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$['address.houseNumber']").value("House number is required"))
                .andExpect(jsonPath("$['address.street']").value("Street is required"))
                .andExpect(jsonPath("$['address.city']").value("City is required"))
                .andExpect(jsonPath("$['address.country']").value("Country is required"))
                .andExpect(jsonPath("$['address.postCode']").value("Post code is required"));
    }

    @Test
    void addHotel_whenContactsRequestIsInvalid() throws Exception {
        var request = new HotelRequest(
                "DoubleTree by Hilton Minsk",
                "The DoubleTree by...",
                "Hilton",
                new AddressRequest(9, "Pobediteley Avenue", "Minsk", "Belarus", "220004"),
                new ContactsRequest("00", ""),
                new ArrivalTimeRequest("14:00", "12:00")
        );

        mockMvc.perform(post("/property-view/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$['contacts.phone']").value("Phone is invalid"))
                .andExpect(jsonPath("$['contacts.email']").value("Email is required"));
    }

    @Test
    void addHotel_whenArrivalTimeRequestIsInvalid() throws Exception {
        var request = new HotelRequest(
                "DoubleTree by Hilton Minsk",
                "The DoubleTree by...",
                "Hilton",
                new AddressRequest(9, "Pobediteley Avenue", "Minsk", "Belarus", "220004"),
                new ContactsRequest("+375 17 309-80-00", "doubletreeminsk.info@hilton.com"),
                new ArrivalTimeRequest("44:00", null)
        );

        mockMvc.perform(post("/property-view/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$['arrivalTime.checkIn']").value("CheckIn time is not valid"))
                .andExpect(jsonPath("$['arrivalTime.checkOut']").doesNotExist());
    }
}