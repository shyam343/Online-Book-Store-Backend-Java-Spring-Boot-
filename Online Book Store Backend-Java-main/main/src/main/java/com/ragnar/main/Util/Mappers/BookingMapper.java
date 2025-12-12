package com.ragnar.main.Util.Mappers;

import com.ragnar.main.API.DTOs.Booking.BookItemDTO;
import com.ragnar.main.API.DTOs.Booking.BookItemResponseDTO;
import com.ragnar.main.Domain.Entities.Bookings;
import com.ragnar.main.Domain.Entities.Books;
import com.ragnar.main.Domain.Entities.Users;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    public Bookings mapBookingDtoToEntity(BookItemDTO model, Users user, Books book) {
        return Bookings.builder()
                .user(user)
                .book(book)
                .bookingDate(model.getBookingDate())
                .dueDate(model.getDueDate())
                .status(model.getStatus())
                .build();
    }

    public BookItemResponseDTO mapEntityToResDTO(Bookings model) {
        var userDetail = new BookItemResponseDTO.UserDetail();
        userDetail.setUserId(model.getUser().getUserId());
        userDetail.setUsername(model.getUser().getUsername());
        userDetail.setFirstName(model.getUser().getFirstName());
        userDetail.setLastName(model.getUser().getLastName());

        var bookDetail = new BookItemResponseDTO.BookDetail();
        bookDetail.setBookId(model.getBook().getBookId());
        bookDetail.setBookName(model.getBook().getBookName());

        return BookItemResponseDTO.builder()
                .bookingId(model.getBookingId())
                .userDetail(userDetail)
                .bookDetail(bookDetail)
                .bookingDate(model.getBookingDate())
                .dueDate(model.getDueDate())
                .status(model.getStatus())
                .build();
    }
}
