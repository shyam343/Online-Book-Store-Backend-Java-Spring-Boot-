package com.ragnar.main.Application.IRepositories;

import com.ragnar.main.Domain.Entities.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBookingRepository extends JpaRepository<Bookings, Long> {

    // Find the lattest unreturned booking of the book
    @Query("""
        SELECT b
        FROM Bookings b
        WHERE b.book.bookId = :bookId
            AND b.status = 'BOOKED'
            AND b.returnDate IS NULL
        ORDER BY b.bookingDate DESC
        LIMIT 1
    """)
    Optional<Bookings> findActiveBookingByBookId(@Param("bookId") Long bookId);
}
