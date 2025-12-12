package com.ragnar.main.API.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ragnar.main.API.Common.TestHelper;
import com.ragnar.main.API.DTOs.Booking.BookItemDTO;
import com.ragnar.main.API.DTOs.Booking.ReturnItemDTO;
import com.ragnar.main.Application.IServices.IAuthService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookingControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

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
    @Order(1)
    void bookItem() throws Exception {
        var bookItemModel = BookItemDTO.builder()
                .bookId(2L)
                .bookingDate(LocalDateTime.now())
                .dueDate(LocalDateTime.now().plusDays(2))
                .build();

        mvc.perform(MockMvcRequestBuilders
            .post("/api/v1/Booking/book")
            .header("Authorization", "Bearer " + testUserJwtToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(bookItemModel))
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @Order(2)
    void getByUsername() throws Exception {
        mvc.perform(MockMvcRequestBuilders
            .get("/api/v1/Booking/by-username")
            .header("Authorization", "Bearer " + testUserJwtToken)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data").exists());
    }


    @Test
    @Order(3)
    void returnItem() throws Exception {
        var returnItemModel = ReturnItemDTO.builder()
                .bookingId(1L)
                .returnDate(LocalDateTime.now())
                .build();

        mvc.perform(MockMvcRequestBuilders
            .post("/api/v1/Booking/return")
            .header("Authorization", "Bearer " + testUserJwtToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(returnItemModel))
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void bookItem_NoAuthHeader_FailCase() throws Exception {
        var bookItemModel = BookItemDTO.builder()
                .bookId(1L)
                .bookingDate(LocalDateTime.now())
                .dueDate(LocalDateTime.now().plusDays(2))
                .build();

        mvc.perform(MockMvcRequestBuilders
            .post("/api/v1/Booking/book")
//            .header("Authorization", "Bearer " + jwtToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(bookItemModel))
        )
        .andDo(print())
        .andExpect(status().isUnauthorized());
    }

    @Test
    void getAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders
            .get("/api/v1/Booking")
            .header("Authorization", "Bearer " + jwtToken)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data").exists());
    }
}