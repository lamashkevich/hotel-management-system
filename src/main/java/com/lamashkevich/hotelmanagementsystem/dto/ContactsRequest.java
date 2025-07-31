package com.lamashkevich.hotelmanagementsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ContactsRequest(

        @Pattern(
                regexp = "^\\+[\\d\\s\\-()]*[\\d][\\d\\s\\-()]*$",
                message = "Phone is invalid"
        )
        @NotBlank(message = "Phone is required")
        String phone,

        @Email(message = "Email is invalid")
        @NotBlank(message = "Email is required")
        String email
) {
}
