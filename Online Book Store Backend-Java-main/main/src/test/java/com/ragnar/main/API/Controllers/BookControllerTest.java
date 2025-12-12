package com.ragnar.main.API.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ragnar.main.API.Common.TestHelper;
import com.ragnar.main.API.DTOs.Book.CreateBookDTO;
import com.ragnar.main.Application.IServices.IAuthService;
import com.ragnar.main.Domain.Enums.GenreType;
import com.ragnar.main.Util.Helpers.DateHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BookControllerTest {
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
    @Order(1)
    void createBook() throws Exception {
        var dto = CreateBookDTO.builder()
                .bookName("Test Book")
                .publicationYear(DateHelper.ParseDate("2025-06-24"))
                .Genre(GenreType.ACTION)
                .Price(1000)
                .authorId(1L)
                .build();

        mvc.perform(MockMvcRequestBuilders
            .post("/api/v1/Book")
            .header("Authorization", "Bearer " + jwtToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(dto))
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data").exists());
    }

    @Test
    @Order(2)
    void deleteBook() throws Exception {
        Long bookId = 1L;
        mvc.perform(MockMvcRequestBuilders
            .delete("/api/v1/Book/" + bookId)
            .header("Authorization", "Bearer " + jwtToken)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data").isEmpty())
        .andExpect(jsonPath("$.message").value("Book deleted with book id: " + bookId));
    }
}