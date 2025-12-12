package com.ragnar.main.Services;

import com.ragnar.main.API.DTOs.Booking.BookItemDTO;
import com.ragnar.main.API.DTOs.Booking.ReturnItemDTO;
import com.ragnar.main.Application.IRepositories.IBookRepository;
import com.ragnar.main.Application.IRepositories.IBookingRepository;
import com.ragnar.main.Application.IRepositories.IUserRepository;
import com.ragnar.main.Application.IServices.IBookingService;
import com.ragnar.main.Domain.Entities.Bookings;
import com.ragnar.main.Domain.Enums.BookingStatus;
import com.ragnar.main.Util.CommonModels.ApiResponse;
import com.ragnar.main.Util.Constants.ResponseCode;
import com.ragnar.main.Util.Helpers.DbLogger;
import com.ragnar.main.Util.Mappers.BookingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {
    private final IBookRepository bookRepository;
    private final IUserRepository userRepository;
    private final IBookingRepository bookingRepository;
    private final DbLogger logger;
    private final BookingMapper bookingMapper;


    @Override
    public ApiResponse<?> BookItem(BookItemDTO dto) {
        try {
            var user = userRepository.findById(dto.getUserId()).orElse(null);
            if (user == null) {
                return ApiResponse.Failed("Invalid User", ResponseCode.IsFailed);
            }

            var book = bookRepository.findById(dto.getBookId()).orElse(null);
            if (book == null) {
                return ApiResponse.Failed("Invalid Book", ResponseCode.IsFailed);
            }

            // Check if the book is already book and is not yet returned
            var activeBookingOpt = bookingRepository.findActiveBookingByBookId(dto.getBookId());
            if (activeBookingOpt.isPresent()) {
                return ApiResponse.Failed("Book is currently unavailable", ResponseCode.IsFailed);
            }

            dto.setStatus(BookingStatus.BOOKED);

            Bookings bookingModel = bookingMapper.mapBookingDtoToEntity(dto, user, book);
            var res = bookingRepository.saveAndFlush(bookingModel);

            var mappedResModel = bookingMapper.mapEntityToResDTO(res);
            return ApiResponse.Success(mappedResModel, "Booked successfullyy", ResponseCode.IsCreated);
        } catch (Exception ex) {
            logger.log(LogLevel.ERROR, ex.getMessage(), "BookingService_BookItem", ex);
            return ApiResponse.Failed("Failed to book: " + ex.getMessage(), ResponseCode.IsFailed);
        }
    }

    @Override
    public ApiResponse<?> ReturnItem(ReturnItemDTO dto) {
        try {
            var existingBooking = bookingRepository.findById(dto.getBookingId()).orElse(null);
            if (existingBooking != null) {
                if (!Objects.equals(existingBooking.getUser().getUserId(), dto.getUserId())) {
                    return ApiResponse.Failed("Incorrect user trying to return book", ResponseCode.IsFailed);
                }
                existingBooking.setStatus(BookingStatus.RETURNED);
                existingBooking.setReturnDate(dto.getReturnDate());

                var res = bookingRepository.saveAndFlush(existingBooking);
                var mappedResModel = bookingMapper.mapEntityToResDTO(res);
                return ApiResponse.Success(mappedResModel, "Book has successfullyy been returned", ResponseCode.IsCreated);
            }
            return ApiResponse.Failed("No booking found", ResponseCode.IsFailed);
        } catch (Exception ex) {
            logger.log(LogLevel.ERROR, ex.getMessage(), "BookingService_ReturnItem", ex);
            return ApiResponse.Failed("Failed to return the booked item: " + ex.getMessage(), ResponseCode.IsFailed);
        }
    }

    @Override
    public ApiResponse<?> GetAllBookings() {
        try {
            var bookings = bookingRepository.findAll();

            var mapped = bookings.stream()
                    .map(bookingMapper::mapEntityToResDTO)
                    .toList();

            return ApiResponse.Success(mapped, "Fetched all bookings", ResponseCode.IsSuccess);
        } catch (Exception ex) {
            logger.log(LogLevel.ERROR, ex.getMessage(), "BookingService_GetAllBookings", ex);
            return ApiResponse.Failed("Failed to fetch bookings: " + ex.getMessage(), ResponseCode.IsFailed);
        }
    }

    @Override
    public ApiResponse<?> GetAllUserBookings(Long userId) {
        try {
            var bookings = bookingRepository.findAll();

            var mapped = bookings.stream()
                    .filter(x -> Objects.equals(x.getUser().getUserId(), userId))
                    .map(bookingMapper::mapEntityToResDTO)
                    .toList();

            return ApiResponse.Success(mapped, "Fetched all bookings for user", ResponseCode.IsSuccess);
        } catch (Exception ex) {
            logger.log(LogLevel.ERROR, ex.getMessage(), "BookingService_GetAllUserBookings", ex);
            return ApiResponse.Failed("Failed to fetch bookings for user: " + ex.getMessage(), ResponseCode.IsFailed);
        }
    }
}
