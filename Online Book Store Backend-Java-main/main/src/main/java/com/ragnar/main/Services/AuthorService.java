package com.ragnar.main.Services;

import com.ragnar.main.API.DTOs.Authors.AuthorResponseDTO;
import com.ragnar.main.API.DTOs.Authors.CreateAuthorDTO;
import com.ragnar.main.API.DTOs.Authors.UpdateAuthorDTO;
import com.ragnar.main.Application.IRepositories.IAuthorRepository;
import com.ragnar.main.Application.IServices.IAuthorService;
import com.ragnar.main.Domain.Entities.Authors;
import com.ragnar.main.Util.CommonModels.ApiResponse;
import com.ragnar.main.Util.Constants.ResponseCode;
import com.ragnar.main.Util.Helpers.DbLogger;
import com.ragnar.main.Util.Mappers.AuthorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService implements IAuthorService {
    private final IAuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final DbLogger logger;

    @Override
    public ApiResponse<AuthorResponseDTO> AddAuthor(CreateAuthorDTO dto) {
        try {
            Authors model = authorMapper.mapDtoToModel(dto);
            Authors savedAuthor = authorRepository.saveAndFlush(model);

            var mappedAuthor = authorMapper.mapEntityToResDto(savedAuthor);
            return ApiResponse.Success(mappedAuthor, "Author created successfullyy", ResponseCode.IsCreated);
        } catch (Exception ex) {
            logger.log(LogLevel.ERROR, ex.getMessage(), "AuthorService_AddAuthor", ex);
            return ApiResponse.Failed("Failed to add author: " + ex.getMessage(), ResponseCode.IsFailed);
        }
    }

    @Override
    public ApiResponse<List<AuthorResponseDTO>> GetAllAuthors() {
        try {
            var authors = authorRepository.findAll();
            if (!authors.isEmpty()) {
                var mappedAuthors = authorMapper.mapResListToResDTO(authors);
                return ApiResponse.Success(mappedAuthors, "Authors fetched", ResponseCode.IsSuccess);
            }
            return ApiResponse.Failed("no author foung", ResponseCode.IsNotFound);
        } catch (Exception ex) {
            logger.log(LogLevel.ERROR, ex.getMessage(), "AuthorService_GetAllAuthors", ex);
            return ApiResponse.Failed("Failed to load authors: " + ex.getMessage(), ResponseCode.IsFailed);
        }
    }

    @Override
    public ApiResponse<AuthorResponseDTO> GetAuthorById(Long authorId) {
        try {
            Authors author = authorRepository.findById(authorId).orElse(null);
            if (author != null) {
                var mappedAuthor = authorMapper.mapEntityToResDto(author);
                return ApiResponse.Success(mappedAuthor, "Author detail fetched", ResponseCode.IsSuccess);
            }
            return ApiResponse.Failed("no author foung", ResponseCode.IsNotFound);
        } catch (Exception ex) {
            logger.log(LogLevel.ERROR, ex.getMessage(), "AuthorService_GetAuthorById", ex);
            return ApiResponse.Failed("Failed to load author: " + ex.getMessage(), ResponseCode.IsFailed);
        }
    }

    @Override
    public ApiResponse<AuthorResponseDTO> UpdateAuthor(UpdateAuthorDTO dto) {
        try {
            Authors existingAuthor = authorRepository.findById(dto.getAuthorId()).orElse(null);

            if (existingAuthor == null) {
                return ApiResponse.Failed("No author found", ResponseCode.IsNotFound);
            }

            // <editor-fold desc="Update existing user data">
            existingAuthor.setAuthorName(dto.getAuthorName());
            // </editor-fold>

            var savedUser = authorRepository.saveAndFlush(existingAuthor);
            var mappedAuthor = authorMapper.mapEntityToResDto(savedUser);
            return ApiResponse.Success(mappedAuthor, "Author updated", ResponseCode.IsSuccess);
        } catch (Exception ex) {
            return ApiResponse.Failed("Failed to update author, Exception occured", ResponseCode.IsError);
        }
    }

    @Override
    public ApiResponse<?> DeleteAuthor(Long authoId) {
        try {
            Authors existingAuthor = authorRepository.findById(authoId).orElse(null);
            if (existingAuthor == null) {
                return ApiResponse.Failed("No author found", ResponseCode.IsNotFound);
            }
            authorRepository.delete(existingAuthor);
            authorRepository.flush();

            return ApiResponse.Success(null, "Author deleted with author id: " + authoId, ResponseCode.IsSuccess);
        } catch (Exception ex) {
            return ApiResponse.Failed("Failed to delete author, Exception occured", ResponseCode.IsError);
        }
    }
}
