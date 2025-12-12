package com.ragnar.main.API.DTOs.Authors;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAuthorDTO {
    @NotBlank
    private String authorName;
}
