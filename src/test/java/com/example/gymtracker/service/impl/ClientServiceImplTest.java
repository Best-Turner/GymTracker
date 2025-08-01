package com.example.gymtracker.service.impl;

import com.example.gymtracker.dto.request.RequestClientDto;
import com.example.gymtracker.dto.response.ResponseClientDto;
import com.example.gymtracker.exception.customException.ClientNotFoundException;
import com.example.gymtracker.mapper.ClientMapper;
import com.example.gymtracker.model.Client;
import com.example.gymtracker.model.Gender;
import com.example.gymtracker.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {
    private static final Long CLIENT_ID = 1L;
    private static final String CLIENT_NAME = "testName";
    private static final LocalDate CLIENT_BIRTHDATE = LocalDate.now().minusYears(10L);
    private Client client;
    private RequestClientDto requestDto =
            new RequestClientDto(CLIENT_NAME, Gender.FEMALE, CLIENT_BIRTHDATE);
    private ResponseClientDto responseDto =
            new ResponseClientDto(CLIENT_ID, CLIENT_NAME, Gender.FEMALE, CLIENT_BIRTHDATE);

    @Mock
    private ClientRepository repository;
    @Mock
    private ClientMapper mapper;
    @InjectMocks
    private ClientServiceImpl service;

    @BeforeEach
    void setUp() {
        client = Client.builder()
                .id(CLIENT_ID)
                .name(CLIENT_NAME)
                .gender(Gender.FEMALE)
                .birthDate(CLIENT_BIRTHDATE)
                .build();

    }

    @Test
    void whenSaveClientThenReturnResponseClientDto() {
        //Arrange
        when(mapper.toEntity(requestDto)).thenReturn(client);
        when(mapper.toDto(client)).thenReturn(responseDto);
        when(repository.save(client)).thenReturn(client);
        //Act
        ResponseClientDto result = service.save(requestDto);
        //Assert
        verify(mapper).toEntity(requestDto);
        verify(mapper).toDto(client);
        verify(repository).save(client);
        assertNotNull(result);
        assertEquals(responseDto, result);
    }


    @Test
    void whenGetClientByValidIdThenReturnResponseClientDto() {
        //Arrange
        when(mapper.toDto(client)).thenReturn(responseDto);
        when(repository.findById(CLIENT_ID)).thenReturn(Optional.of(client));
        //Act
        ResponseClientDto result = service.getById(CLIENT_ID);
        //Assert
        verify(mapper).toDto(client);
        verify(repository).findById(CLIENT_ID);
        assertNotNull(result);
        assertEquals(responseDto, result);
    }

    @Test
    void whenGetClientByInvalidIdThenThrowClientNotFountException() {
        String expectedMessage = "Client not found with id:" + CLIENT_ID;
        //Arrange
        when(repository.findById(CLIENT_ID)).thenReturn(Optional.empty());
        //Act
        //Assert
        ClientNotFoundException error = assertThrows(ClientNotFoundException.class,
                () -> service.getById(CLIENT_ID), expectedMessage);
        verifyNoInteractions(mapper);
        assertEquals(expectedMessage, error.getMessage());
    }

    @Test
    void whenGetAllClientsThenReturnListResponseClientDto() {
        //Arrange
        when(repository.findAll()).thenReturn(Collections.singletonList(client));
        when(mapper.toDto(client)).thenReturn(responseDto);
        //Act
        List<ResponseClientDto> result = service.getAll();
        //Assert
        verify(repository).findAll();
        verify(mapper).toDto(client);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(responseDto, result.get(0));
    }


    @Test
    void whenDeleteClientWithValidId() {
        //Arrange
        when(repository.findById(CLIENT_ID)).thenReturn(Optional.of(client));
        doNothing().when(repository).deleteById(CLIENT_ID);
        //Act
        service.deleteById(CLIENT_ID);
        //Assert
        verify(repository).findById(CLIENT_ID);
        verify(repository).deleteById(CLIENT_ID);
    }

    @Test
    void whenDeleteClientWithInvalidIdThenThrowClientNotFoundException() {
        //Arrange
        String expectedMessage = "Client not found with id:" + CLIENT_ID;
        when(repository.findById(CLIENT_ID)).thenReturn(Optional.empty());
        //Act & Assert
        ClientNotFoundException exception = assertThrows(ClientNotFoundException.class,
                () -> service.deleteById(CLIENT_ID), expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
        verify(repository).findById(CLIENT_ID);
        verify(repository, never()).deleteById(any());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void whenClientExistsReturnsTrue() {
        // Arrange
        when(repository.findById(CLIENT_ID))
                .thenReturn(Optional.of(client));
        // Act
        boolean result = service.checkExists(CLIENT_ID);
        // Assert
        assertTrue(result);
        verify(repository).findById(CLIENT_ID);
    }

    @Test
    void whenClientNotExistsThrowsException() {
        // Arrange
        String expectedMessage = "Client not found with id:" + CLIENT_ID;
        when(repository.findById(CLIENT_ID))
                .thenReturn(Optional.empty());

        // Act & Assert
        ClientNotFoundException exception = assertThrows(
                ClientNotFoundException.class,
                () -> service.getClientById(CLIENT_ID)
        );
        assertEquals(expectedMessage, exception.getMessage());
        verify(repository).findById(CLIENT_ID);
    }


    @Test
    void update_WithValidData_ReturnsUpdatedClientDto() {
        // Arrange
        RequestClientDto updateDto = new RequestClientDto(
                "New Name",
                Gender.MALE, CLIENT_BIRTHDATE.minusDays(10)
        );
        when(repository.findById(CLIENT_ID)).thenReturn(Optional.of(client));
        when(mapper.toDto(client)).thenReturn(responseDto);
        doNothing().when(mapper).updateFromClientDto(updateDto, client);

        // Act
        ResponseClientDto result = service.update(CLIENT_ID, updateDto);

        // Assert
        verify(repository).findById(CLIENT_ID);
        verify(mapper).updateFromClientDto(updateDto, client);
        verify(repository).save(client);
        verify(mapper).toDto(client);

        assertEquals(responseDto, result);
    }
}