package br.com.fiap.techchallenge.services.impl;

import br.com.fiap.techchallenge.controllers.dto.ClienteRequestDTO;
import br.com.fiap.techchallenge.domain.Cliente;
import br.com.fiap.techchallenge.exceptions.DatabaseOperationException;
import br.com.fiap.techchallenge.exceptions.UserNotFoundException;
import br.com.fiap.techchallenge.repositories.ClienteRepository;
import br.com.fiap.techchallenge.repositories.UsuarioRepository;
import br.com.fiap.techchallenge.services.EnderecoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private EnderecoService enderecoService;
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private ClienteRequestDTO clienteRequestDTO;
    private Cliente clienteMock;
    private final Long clienteId = 1L;

    @BeforeEach
    void setup() {
        clienteRequestDTO = new ClienteRequestDTO("João", "joao@email.com", "senha123", null);

        clienteMock = new Cliente();
        clienteMock.setId(clienteId);
        clienteMock.setNome(clienteRequestDTO.getNome());
        clienteMock.setEmail(clienteRequestDTO.getEmail());
        clienteMock.setSenha("senha_criptografada");
    }

    @Test
    @DisplayName("Deve criar um cliente e retornar o DTO em caso de sucesso")
    void shouldCreateClienteAndReturnDTO() {
        when(usuarioRepository.findByEmail(clienteRequestDTO.getEmail())).thenReturn(Optional.empty());

        when(passwordEncoder.encode(clienteRequestDTO.getSenha())).thenReturn("senha_criptografada");

        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteMock);

        var resultado = clienteService.create(clienteRequestDTO);

        assertNotNull(resultado);
        assertEquals(clienteId, resultado.getId());
        assertEquals("João", resultado.getNome());

        verify(passwordEncoder, times(1)).encode(clienteRequestDTO.getSenha());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
        verify(usuarioRepository, times(1)).findByEmail(clienteRequestDTO.getEmail());
    }

    @Test
    @DisplayName("Deve lançar DatabaseOperationException se o save falhar")
    void shouldThrowExceptionWhenSaveFails() {
        when(usuarioRepository.findByEmail(clienteRequestDTO.getEmail())).thenReturn(Optional.empty());

        when(passwordEncoder.encode(anyString())).thenReturn("senha_criptografada");
        when(clienteRepository.save(any(Cliente.class))).thenThrow(new RuntimeException("Erro de DB"));

        assertThrows(DatabaseOperationException.class, () -> {
            clienteService.create(clienteRequestDTO);
        });

        verify(clienteRepository, times(1)).save(any(Cliente.class));
        verify(enderecoService, never()).save(anyLong(), any());
    }

    @Test
    @DisplayName("Deve deletar o cliente com sucesso quando ID existir")
    void shouldDeleteClienteSuccessfully() {
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteMock));

        clienteService.delete(clienteId);

        verify(clienteRepository, times(1)).delete(clienteMock);
    }

    @Test
    @DisplayName("Deve lançar UserNotFoundException se o ID não existir ao deletar")
    void shouldThrowExceptionWhenDeleteIdNotFound() {
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            clienteService.delete(clienteId);
        });

        verify(clienteRepository, never()).delete(any(Cliente.class));
    }
}