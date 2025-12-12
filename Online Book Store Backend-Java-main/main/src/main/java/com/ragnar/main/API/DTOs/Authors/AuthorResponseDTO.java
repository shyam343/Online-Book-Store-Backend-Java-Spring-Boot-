package com.ragnar.main.API.DTOs.Authors;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorResponseDTO {
    private Long authorId;
    private String authorName;
}

