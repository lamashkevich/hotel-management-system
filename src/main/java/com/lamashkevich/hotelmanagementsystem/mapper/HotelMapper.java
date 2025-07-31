package com.lamashkevich.hotelmanagementsystem.mapper;

import com.lamashkevich.hotelmanagementsystem.dto.HotelPreviewResponse;
import com.lamashkevich.hotelmanagementsystem.dto.HotelRequest;
import com.lamashkevich.hotelmanagementsystem.dto.HotelResponse;
import com.lamashkevich.hotelmanagementsystem.entity.Hotel;

public interface HotelMapper {

    HotelResponse entityToResponse(Hotel hotel);
    HotelPreviewResponse entityToPreviewResponse(Hotel hotel);
    Hotel requestToEntity(HotelRequest hotelRequest);
}
