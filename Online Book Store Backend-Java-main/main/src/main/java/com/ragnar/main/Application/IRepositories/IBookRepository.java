package com.ragnar.main.Application.IRepositories;

import com.ragnar.main.Domain.Entities.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookRepository extends JpaRepository<Books, Long> {
    @Query("""
        SELECT b
        FROM Books b
        LEFT JOIN b.bookings bk
        WITH bk.status = com.ragnar.main.Domain.Enums.BookingStatus.BOOKED
        WHERE bk IS NULL
    """)
    List<Books> GetAllAvailableBooks();

    @Query("""
        SELECT DISTINCT b
        FROM Books b
        JOIN b.bookings bk
        WHERE bk.status = com.ragnar.main.Domain.Enums.BookingStatus.BOOKED
    """)
    List<Books> GetAllUnavailableBooks();

    List<Books> findByBookNameContainingIgnoreCase(String name);
}
