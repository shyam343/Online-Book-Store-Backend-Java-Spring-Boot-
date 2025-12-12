package com.ragnar.main.Util.Mappers;

import com.ragnar.main.API.DTOs.Auth.SignupDTO;
import com.ragnar.main.Domain.Entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Users mapSignupToUsers(SignupDTO model) {
        return Users.builder()
                .username(model.getUsername())
                .passwordHash(passwordEncoder.encode(model.getPassword()))
                .email(model.getEmail())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .address(model.getAddress())
                .build();
    }


}
