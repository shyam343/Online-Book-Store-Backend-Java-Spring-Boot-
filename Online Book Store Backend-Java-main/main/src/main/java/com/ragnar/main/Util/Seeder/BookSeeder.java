package com.ragnar.main.Util.Seeder;

import com.ragnar.main.Application.IRepositories.IAuthorRepository;
import com.ragnar.main.Application.IRepositories.IBookRepository;
import com.ragnar.main.Domain.Entities.Books;
import com.ragnar.main.Domain.Enums.GenreType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookSeeder {
    private final IBookRepository bookRepository;
    private final IAuthorRepository authorRepository;

    public void SeedBooks() {
        if (bookRepository.count() == 0) {

            // Dummy date for now
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date publicationDate;
            try {
                publicationDate = sdf.parse("2025-06-23");
            } catch (ParseException e) {
                throw new RuntimeException("Invalid date format", e);
            }


            var author1 = authorRepository.findAll().get(0);
            var author2 = authorRepository.findAll().get(1);
            var author3 = authorRepository.findAll().get(2);

            bookRepository.saveAll(List.of(
                new Books(null, "The Lord of the Rings", publicationDate, GenreType.ACTION, 100, author1, null),
                new Books(null, "A Song of Ice and Fire", publicationDate, GenreType.FANTASY, 200, author2, null),
                new Books(null, "Harry Potter", publicationDate, GenreType.THRILLER, 500, author3, null)
            ));

            System.out.println("Default books seeded.");
        }
    }
}
