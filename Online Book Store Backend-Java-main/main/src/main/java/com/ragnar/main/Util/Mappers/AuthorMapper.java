package com.ragnar.main.Util.Mappers;

import com.ragnar.main.API.DTOs.Authors.AuthorResponseDTO;
import com.ragnar.main.API.DTOs.Authors.CreateAuthorDTO;
import com.ragnar.main.Domain.Entities.Authors;
import com.ragnar.main.Domain.Entities.Books;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorMapper {
    public Authors mapDtoToModel(CreateAuthorDTO dto) {
        return Authors.builder()
                .authorName(dto.getAuthorName())
                .build();
    }

    public AuthorResponseDTO mapEntityToResDto(Authors model) {
        return AuthorResponseDTO.builder()
                .authorId(model.getAuthorId())
                .authorName(model.getAuthorName())
                .build();
    }

    public List<AuthorResponseDTO> mapResListToResDTO(List<Authors> modelList){
        List<AuthorResponseDTO> authorList = new ArrayList<>();
        for (Authors author : modelList) {
            authorList.add(mapEntityToResDto(author));
        }
        return authorList;
    }
}
