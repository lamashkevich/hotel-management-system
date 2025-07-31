package com.lamashkevich.hotelmanagementsystem.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HotelRequest(
        @NotBlank(message = "Name is required")
        String name,

        String description,

        @NotBlank(message = "Brand is required")
        String brand,

        @Valid
        @NotNull(message = "Address is required")
        AddressRequest address,

        @Valid
        @NotNull(message = "Contacts is required")
        ContactsRequest contacts,

        @Valid
        @NotNull(message = "Arrival time is required")
        ArrivalTimeRequest arrivalTime
) {
}
