package com.ragnar.main.Application.IServices;

import com.ragnar.main.API.DTOs.Authors.AuthorResponseDTO;
import com.ragnar.main.API.DTOs.Authors.CreateAuthorDTO;
import com.ragnar.main.API.DTOs.Authors.UpdateAuthorDTO;
import com.ragnar.main.Domain.Entities.Authors;
import com.ragnar.main.Util.CommonModels.ApiResponse;

import java.util.List;

public interface IAuthorService {
    ApiResponse<AuthorResponseDTO> AddAuthor(CreateAuthorDTO dto);
    ApiResponse<List<AuthorResponseDTO>> GetAllAuthors();
    ApiResponse<AuthorResponseDTO> GetAuthorById(Long authorId);
    ApiResponse<AuthorResponseDTO> UpdateAuthor(UpdateAuthorDTO dto);
    ApiResponse<?> DeleteAuthor(Long authoId);
}
