package br.com.fiap.techchallenge.services.impl;

import br.com.fiap.techchallenge.controllers.dto.DonoRestauranteRequestDTO;
import br.com.fiap.techchallenge.controllers.dto.DonoRestauranteResponseDTO;
import br.com.fiap.techchallenge.domain.DonoRestaurante;
import br.com.fiap.techchallenge.exceptions.DatabaseOperationException;
import br.com.fiap.techchallenge.exceptions.UserNotFoundException;
import br.com.fiap.techchallenge.repositories.DonoRestauranteRepository;
import br.com.fiap.techchallenge.repositories.UsuarioRepository;
import br.com.fiap.techchallenge.services.EnderecoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DonoRestauranteServiceImplTest {

    @Mock
    private DonoRestauranteRepository donoRestauranteRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private EnderecoService enderecoService;
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private DonoRestauranteServiceImpl donoRestauranteService;

    private DonoRestauranteRequestDTO requestDTO;
    private DonoRestaurante donoRestauranteMock;
    private final Long donoId = 2L;

    @BeforeEach
    void setup() {
        requestDTO = new DonoRestauranteRequestDTO("Carlos Restaurante", "carlos@rest.com", "senhaforte", null);

        donoRestauranteMock = new DonoRestaurante();
        donoRestauranteMock.setId(donoId);
        donoRestauranteMock.setNome(requestDTO.nome());
        donoRestauranteMock.setEmail(requestDTO.email());
        donoRestauranteMock.setSenha("senha_criptografada");
    }

    @Test
    @DisplayName("Deve criar um DonoRestaurante e retornar o DTO de sucesso")
    void shouldCreateDonoRestauranteAndReturnDTO() {
        when(usuarioRepository.findByEmail(requestDTO.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(requestDTO.senha())).thenReturn("senha_criptografada");
        when(donoRestauranteRepository.save(any(DonoRestaurante.class))).thenReturn(donoRestauranteMock);

        DonoRestauranteResponseDTO resultado = DonoRestauranteResponseDTO.fromDomain(donoRestauranteService.create(requestDTO));

        assertNotNull(resultado);
        assertEquals(donoId, resultado.id());
        assertEquals("Carlos Restaurante", resultado.nome());

        verify(usuarioRepository, times(1)).findByEmail(requestDTO.email());
        verify(passwordEncoder, times(1)).encode(requestDTO.senha());
        verify(donoRestauranteRepository, times(1)).save(any(DonoRestaurante.class));
        verify(enderecoService, never()).save(anyLong(), any());
    }

    @Test
    @DisplayName("Deve lançar DatabaseOperationException se o save falhar")
    void shouldThrowExceptionWhenCreateFails() {
        when(usuarioRepository.findByEmail(requestDTO.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("senha_criptografada");
        when(donoRestauranteRepository.save(any(DonoRestaurante.class))).thenThrow(new RuntimeException("Falha de persistência"));

        assertThrows(DatabaseOperationException.class, () -> {
            donoRestauranteService.create(requestDTO);
        });

        verify(donoRestauranteRepository, times(1)).save(any(DonoRestaurante.class));
        verify(enderecoService, never()).save(anyLong(), any());
    }

    @Test
    @DisplayName("Deve retornar DonoRestauranteResponseDTO ao buscar por ID existente")
    void shouldReturnDonoRestauranteById() {
        when(donoRestauranteRepository.findById(donoId)).thenReturn(Optional.of(donoRestauranteMock));

        DonoRestauranteResponseDTO resultado = DonoRestauranteResponseDTO.fromDomain(donoRestauranteService.getById(donoId));

        assertNotNull(resultado);
        assertEquals(donoId, resultado.id());
        verify(donoRestauranteRepository, times(1)).findById(donoId);
    }

    @Test
    @DisplayName("Deve lançar UserNotFoundException ao buscar por ID inexistente")
    void shouldThrowExceptionWhenIdNotFound() {
        when(donoRestauranteRepository.findById(donoId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            donoRestauranteService.getById(donoId);
        });
        verify(donoRestauranteRepository, times(1)).findById(donoId);
    }

    @Test
    @DisplayName("Deve retornar lista de DonoRestaurantes no getAll")
    void shouldReturnAllDonoRestaurantes() {
        when(donoRestauranteRepository.findAll()).thenReturn(List.of(donoRestauranteMock));

        List<DonoRestaurante> resultados = donoRestauranteService.getAll();

        assertFalse(resultados.isEmpty());
        assertEquals(1, resultados.size());
        verify(donoRestauranteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve deletar DonoRestaurante com sucesso quando ID existir")
    void shouldDeleteDonoRestauranteSuccessfully() {
        when(donoRestauranteRepository.findById(donoId)).thenReturn(Optional.of(donoRestauranteMock));

        donoRestauranteService.delete(donoId);

        verify(donoRestauranteRepository, times(1)).deleteById(donoId);
    }

    @Test
    @DisplayName("Deve lançar UserNotFoundException se o ID não existir ao deletar")
    void shouldThrowExceptionWhenDeleteIdNotFound() {
        when(donoRestauranteRepository.findById(donoId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            donoRestauranteService.delete(donoId);
        });

        verify(donoRestauranteRepository, never()).deleteById(anyLong());
    }
}
