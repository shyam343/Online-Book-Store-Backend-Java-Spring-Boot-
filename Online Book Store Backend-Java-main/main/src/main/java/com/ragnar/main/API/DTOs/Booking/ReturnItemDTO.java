package com.ragnar.main.API.DTOs.Booking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ragnar.main.Domain.Enums.BookingStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReturnItemDTO {
    @JsonIgnore
    private Long userId;

    @NotBlank
    private Long bookingId;

    @NotBlank
    private LocalDateTime returnDate;

    @JsonIgnore
    private BookingStatus status;
}
