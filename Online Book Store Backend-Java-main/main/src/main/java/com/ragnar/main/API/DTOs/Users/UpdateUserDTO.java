package com.ragnar.main.API.DTOs.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserDTO {
    @JsonIgnore
    private Long userId;

    @Email
    private String email;
    private String firstName;
    private String lastName;
    private String address;
}
