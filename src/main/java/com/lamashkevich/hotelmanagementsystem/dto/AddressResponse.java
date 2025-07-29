package com.lamashkevich.hotelmanagementsystem.dto;

public record AddressResponse(
        Integer houseNumber,
        String street,
        String city,
        String country,
        String postCode
) {
}
