package com.ragnar.main.API.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ragnar.main.API.Common.TestHelper;
import com.ragnar.main.API.DTOs.Authors.CreateAuthorDTO;
import com.ragnar.main.Application.IServices.IAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthorControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private IAuthService authService;

    @Autowired
    private TestHelper testHelper;

    private String jwtToken;


    @BeforeEach
    void setup() {
        jwtToken = testHelper.GetJwt();
    }

    @Test
    void createAuthor() throws Exception {
        var authorModel = CreateAuthorDTO.builder()
                .authorName("Test Author")
                .build();

        mvc.perform(MockMvcRequestBuilders
            .post("/api/v1/Author")
            .header("Authorization", "Bearer " + jwtToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(authorModel))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void getAllAuthors() throws Exception {
        mvc.perform(MockMvcRequestBuilders
            .get("/api/v1/Author")
            .header("Authorization", "Bearer " + jwtToken)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data").exists());
    }
}