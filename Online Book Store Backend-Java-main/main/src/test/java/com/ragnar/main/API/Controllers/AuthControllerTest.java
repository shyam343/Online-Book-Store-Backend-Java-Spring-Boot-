package com.ragnar.main.API.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ragnar.main.API.DTOs.Auth.LoginDTO;
import com.ragnar.main.API.DTOs.Auth.SignupDTO;
import com.ragnar.main.Application.IRepositories.IUserRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private IUserRepository userRepository;

    @Test
    @Order(1)
    void signup() throws Exception{
        var signupModel = SignupDTO.builder()
                .username("testuser")
                .password("testuser")
                .email("test@user.com")
                .firstName("test")
                .lastName("user")
                .phone("9800000000")
                .address("testaddress")
                .build();

        mvc.perform(MockMvcRequestBuilders
            .post("/api/v1/Auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(signupModel))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @Order(2)
    void login() throws Exception {
        var loginModel = LoginDTO.builder()
                .username("superadmin")
                .password("superadmin@123")
                .build();

        mvc.perform(MockMvcRequestBuilders
            .post("/api/v1/Auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(loginModel))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data").isNotEmpty());
    }
}