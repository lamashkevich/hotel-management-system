package com.lamashkevich.hotelmanagementsystem.dto;

public record HotelPreviewResponse(
        Long id,
        String name,
        String description,
        String address,
        String phone
) {
}
