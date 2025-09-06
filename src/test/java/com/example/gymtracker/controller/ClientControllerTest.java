package com.example.gymtracker.controller;

import com.example.gymtracker.dto.request.RequestClientDto;
import com.example.gymtracker.dto.response.ResponseClientDto;
import com.example.gymtracker.model.Client;
import com.example.gymtracker.model.Gender;
import com.example.gymtracker.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    private static final Long CLIENT_ID = 1L;
    private static final LocalDate DATE = LocalDate.now().minusYears(10);
    private static final String CLIENT_NAME = "someName";
    private static final Gender CLIENT_GENDER = Gender.FEMALE;
    private static final LocalDate BIRTH_DATE = LocalDate.of(2025, 9, 06);

    private static final String BASE_URL = "/api/clients";
    private  RequestClientDto requestClientDto;
    private  ResponseClientDto responseClientDto;
    private Client client;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService service;


    @BeforeEach
    void setUp() {
        client = Client.builder()
                .id(CLIENT_ID)
                .name(CLIENT_NAME)
                .gender(Gender.MALE)
                .birthDate(DATE)
                .build();
        requestClientDto = new RequestClientDto(CLIENT_NAME, CLIENT_GENDER, BIRTH_DATE);
        responseClientDto = new ResponseClientDto(CLIENT_ID, CLIENT_NAME, CLIENT_GENDER, BIRTH_DATE);
    }

    @Test
    void whenGetClientByIdThenReturnHttpStatusOK() throws Exception {
        // Arrange
        when(service.getById(CLIENT_ID)).thenReturn(new ResponseClientDto(CLIENT_ID, CLIENT_NAME, Gender.MALE, DATE));
        // Act & Assert
        mockMvc.perform(get(BASE_URL.concat("/1"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(CLIENT_ID))
                .andExpect(jsonPath("$.name").value(CLIENT_NAME))
                .andExpect(jsonPath("$.birthDate").value(DATE.toString()));
    }


    @Test
    void whenSaveClientThenReturnResponseClientDto() throws Exception {

        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        when(service.save(requestClientDto)).thenReturn(responseClientDto);
        // Act & Assert
        mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestClientDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(CLIENT_ID))
                .andExpect(jsonPath("$.name").value(CLIENT_NAME))
                .andExpect(jsonPath("$.birthDate").value(BIRTH_DATE.toString()));
    }

}