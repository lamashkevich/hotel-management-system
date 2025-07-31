package com.lamashkevich.hotelmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ArrivalTimeRequest(
        @Pattern(
                regexp = "^([0-1][0-9]|2[0-3]):[0-5][0-9]$",
                message = "CheckIn time is not valid"
        )
        @NotBlank(message = "CheckIn is required")
        String checkIn,

        @Pattern(
                regexp = "^([0-1][0-9]|2[0-3]):[0-5][0-9]$",
                message = "CheckOut time is not valid"
        )
        String checkOut
) {
}
