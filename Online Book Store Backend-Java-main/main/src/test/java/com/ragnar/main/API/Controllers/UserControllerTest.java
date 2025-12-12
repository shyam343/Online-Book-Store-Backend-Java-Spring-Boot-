package com.ragnar.main.API.Controllers;//package com.ragnar.main.API.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ragnar.main.API.Common.TestHelper;
import com.ragnar.main.API.DTOs.Auth.LoginDTO;
import com.ragnar.main.Application.IRepositories.IUserRepository;
import com.ragnar.main.Application.IServices.IAuthService;
import com.ragnar.main.Domain.Entities.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IAuthService authService;

    @Autowired
    private TestHelper testHelper;

    private String jwtToken;
    private String testUserJwtToken;

    @BeforeEach
    void setup() {
        jwtToken = testHelper.GetJwt();
        testUserJwtToken = testHelper.GetTestUserJwt();
    }


    @Test
    void getAllUsers() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/User/get-all")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty());


    }
    @Test
    void getUserDetail() throws Exception  {
        Users user = userRepository.findByUsername("actualtestuser");
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/User/" + user.getUsername())
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void updateUser() throws Exception {
        Users user = userRepository.findByUsername("actualtestuser");
        user.setEmail("newemail@example.com");
        String userJson = mapper.writeValueAsString(user);

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/User")
                        .header("Authorization", "Bearer " + testUserJwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }
}
