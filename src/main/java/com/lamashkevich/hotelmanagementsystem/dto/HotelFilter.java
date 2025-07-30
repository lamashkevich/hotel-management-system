package com.lamashkevich.hotelmanagementsystem.dto;

import java.util.List;

public record HotelFilter(
        String name,
        String brand,
        String city,
        String country,
        List<String> amenities
) {
}
