package com.ragnar.main.API.DTOs.Booking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ragnar.main.Domain.Enums.BookingStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookItemDTO {
    @JsonIgnore
    private Long userId;

    @NotBlank
    private Long bookId;

    @NotBlank
    private LocalDateTime bookingDate;

    @NotBlank
    private LocalDateTime dueDate;

    @JsonIgnore
    private BookingStatus status;
}
