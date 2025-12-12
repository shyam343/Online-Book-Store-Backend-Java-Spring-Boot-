package com.ragnar.main.Util.Mappers;

import com.ragnar.main.API.DTOs.Users.UsersResponseDTO;
import com.ragnar.main.Domain.Entities.Users;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    public List<UsersResponseDTO> mapListToUserResDTO(List<Users> modelList) {
        List<UsersResponseDTO> usersList = new ArrayList<>();

        for (Users user : modelList) {
            usersList.add(mapToUserResDTO(user));
        }
        return usersList;
    }

    public UsersResponseDTO mapToUserResDTO(Users model) {
        return UsersResponseDTO.builder()
                .userId(model.getUserId())
                .username(model.getUsername())
                .email(model.getEmail())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .address(model.getAddress())
                .roles(model.getRoles().stream().map(role -> role.getRoleName().name()).toList())
//                .isDeleted(model.isDeleted())
                .build();
    }
}
