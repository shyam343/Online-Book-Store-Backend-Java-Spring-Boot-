package com.ragnar.main.API.DTOs.Book;

import com.ragnar.main.Domain.Enums.GenreType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class UpdateBookDTO {
    @NotBlank
    private Long bookId;

    private String bookName;

    private Date publicationYear;

    @Enumerated(value = EnumType.STRING)
    private GenreType Genre;

    private String Price;

    private String authorId;
}
