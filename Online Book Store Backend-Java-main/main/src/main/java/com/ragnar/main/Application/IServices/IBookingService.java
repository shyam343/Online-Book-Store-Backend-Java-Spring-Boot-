package com.ragnar.main.Application.IServices;

import com.ragnar.main.API.DTOs.Booking.BookItemDTO;
import com.ragnar.main.API.DTOs.Booking.ReturnItemDTO;
import com.ragnar.main.Util.CommonModels.ApiResponse;

public interface IBookingService {
    ApiResponse<?> BookItem(BookItemDTO dto);
    ApiResponse<?> ReturnItem(ReturnItemDTO dto);
    ApiResponse<?> GetAllBookings();
    ApiResponse<?> GetAllUserBookings(Long userId);
}
