package com.ragnar.main.API.DTOs.Booking;

import com.ragnar.main.Domain.Enums.BookingStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookItemResponseDTO {
    @Data
    public static class UserDetail {
        private Long userId;
        private String username;
        private String firstName;
        private String lastName;
    }

    @Data
    public static class BookDetail {
        private Long bookId;
        private String bookName;
    }

    private Long bookingId;
    private UserDetail userDetail;
    private BookDetail bookDetail;
    private LocalDateTime bookingDate;
    private LocalDateTime dueDate;
    private BookingStatus status;
}
