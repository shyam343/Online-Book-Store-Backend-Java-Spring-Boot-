package com.ragnar.main.API.DTOs.Authors;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateAuthorDTO {
    @NotBlank
    Long authorId;

    @NotBlank
    String authorName;
}
