package com.lamashkevich.hotelmanagementsystem.mapper;

import com.lamashkevich.hotelmanagementsystem.dto.*;
import com.lamashkevich.hotelmanagementsystem.entity.*;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class HotelMapperImpl implements HotelMapper {

    @Override
    public HotelResponse entityToResponse(Hotel hotel) {
        if (hotel == null) return null;

        return new HotelResponse(
                hotel.getId(),
                hotel.getName(),
                hotel.getDescription(),
                hotel.getBrand(),
                addressEntityToResponse(hotel.getAddress()),
                contactsEntityToResponse(hotel.getContacts()),
                arrivalTimeEntityToResponse(hotel.getArrivalTime()),
                amenitiesToStringsSet(hotel.getAmenities())
        );
    }

    @Override
    public HotelPreviewResponse entityToPreviewResponse(Hotel hotel) {
        if (hotel == null) return null;

        var addressPreview = "%d %s, %s, %s, %s".formatted(
                hotel.getAddress().getHouseNumber(),
                hotel.getAddress().getStreet(),
                hotel.getAddress().getCity(),
                hotel.getAddress().getPostCode(),
                hotel.getAddress().getCountry()
        );

        return new HotelPreviewResponse(
                hotel.getId(),
                hotel.getName(),
                hotel.getDescription(),
                addressPreview,
                hotel.getContacts().getPhone()
        );
    }

    @Override
    public Hotel requestToEntity(HotelRequest hotelRequest) {

        if (hotelRequest == null) return null;

        return Hotel.builder()
                .name(hotelRequest.name())
                .description(hotelRequest.description())
                .brand(hotelRequest.brand())
                .address(addressRequestToEntity(hotelRequest.address()))
                .contacts(contactsRequestToEntity(hotelRequest.contacts()))
                .arrivalTime(arrivalTimeRequestToEntity(hotelRequest.arrivalTime()))
                .build();
    }

    private Set<String> amenitiesToStringsSet(Set<HotelAmenity> amenities) {
        return amenities.stream()
                .map(HotelAmenity::getName)
                .collect(Collectors.toSet());
    }

    private Address addressRequestToEntity(AddressRequest addressRequest) {

        if (addressRequest == null) return null;

        return Address.builder()
                .houseNumber(addressRequest.houseNumber())
                .street(addressRequest.street())
                .city(addressRequest.city())
                .country(addressRequest.country())
                .postCode(addressRequest.postCode())
                .build();
    }

    private Contacts contactsRequestToEntity(ContactsRequest contactsRequest) {

        if (contactsRequest == null) return null;

        return Contacts.builder()
                .email(contactsRequest.email())
                .phone(contactsRequest.phone())
                .build();
    }

    private ArrivalTime arrivalTimeRequestToEntity(ArrivalTimeRequest arrivalTimeRequest) {

        if (arrivalTimeRequest == null) return null;

        return ArrivalTime.builder()
                .checkIn(arrivalTimeRequest.checkIn())
                .checkOut(arrivalTimeRequest.checkOut())
                .build();
    }

    private ContactsResponse contactsEntityToResponse(Contacts contacts) {

        if (contacts == null) return null;

        return new ContactsResponse(
                contacts.getPhone(),
                contacts.getEmail()
        );
    }

    private AddressResponse addressEntityToResponse(Address address) {

        if (address == null) return null;

        return new AddressResponse(
                address.getHouseNumber(),
                address.getStreet(),
                address.getCity(),
                address.getCountry(),
                address.getPostCode()
        );
    }

    private ArrivalTimeResponse arrivalTimeEntityToResponse(ArrivalTime arrivalTime) {

        if (arrivalTime == null) return null;

        return new ArrivalTimeResponse(
                arrivalTime.getCheckIn(),
                arrivalTime.getCheckOut()
        );
    }
}
