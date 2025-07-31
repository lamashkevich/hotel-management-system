package com.lamashkevich.hotelmanagementsystem.dto;

import java.util.Set;

public record HotelResponse(
        Long id,
        String name,
        String description,
        String brand,
        AddressResponse address,
        ContactsResponse contacts,
        ArrivalTimeResponse arrivalTime,
        Set<String> amenities
) {
}
