package com.ragnar.main.API.DTOs.Book;

import com.ragnar.main.Domain.Enums.GenreType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CreateBookDTO {
    @NotBlank
    private String bookName;

    @NotBlank
    private Date publicationYear;

    @NotBlank
    @Enumerated(value = EnumType.STRING)
    private GenreType Genre;

    @NotBlank
    private double Price;

    @NotBlank
    private Long authorId;
}
