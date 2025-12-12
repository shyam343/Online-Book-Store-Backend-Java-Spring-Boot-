package com.ragnar.main.API.DTOs.Book;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponseDTO {

    @Data
    public static class AuthorDetail {
        private String authorId;
        private String authorName;
    }

    private Long bookId;
    private String bookName;
    private String genre;
    private double price;
    private AuthorDetail authorDetail;
}


