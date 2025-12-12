package com.ragnar.main.API.DTOs.Users;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UsersResponseDTO {
    private Long userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
//    private boolean isDeleted = false;
    private List<String> roles;
}
