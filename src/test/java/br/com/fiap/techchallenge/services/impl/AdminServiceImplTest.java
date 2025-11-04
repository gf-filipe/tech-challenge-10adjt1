package br.com.fiap.techchallenge.services.impl;

import br.com.fiap.techchallenge.controllers.dto.AdminRequestDTO;
import br.com.fiap.techchallenge.controllers.dto.AdminResponseDTO;
import br.com.fiap.techchallenge.domain.Admin;
import br.com.fiap.techchallenge.exceptions.DatabaseOperationException;
import br.com.fiap.techchallenge.exceptions.UserNotFoundException;
import br.com.fiap.techchallenge.repositories.AdminRepository;
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
class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private EnderecoService enderecoService;
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    private AdminRequestDTO adminRequestDTO;
    private Admin adminMock;
    private final Long adminId = 1L;

    @BeforeEach
    void setup() {
        adminRequestDTO = new AdminRequestDTO("Ana Admin", "ana@admin.com", "segredo", null);

        adminMock = new Admin();
        BeanUtils.copyProperties(adminRequestDTO, adminMock);
        adminMock.setId(adminId);
        adminMock.setSenha("senha_criptografada");
    }

    @Test
    @DisplayName("Deve criar um Admin e retornar o DTO de sucesso")
    void shouldCreateAdminAndReturnDTO() {
        when(usuarioRepository.findByEmail(adminRequestDTO.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(adminRequestDTO.getSenha())).thenReturn("senha_criptografada");
        when(adminRepository.save(any(Admin.class))).thenReturn(adminMock);

        AdminResponseDTO resultado = adminService.create(adminRequestDTO);

        assertNotNull(resultado);
        assertEquals(adminId, resultado.getId());

        verify(usuarioRepository, times(1)).findByEmail(adminRequestDTO.getEmail());
        verify(passwordEncoder, times(1)).encode(adminRequestDTO.getSenha());
        verify(adminRepository, times(1)).save(any(Admin.class));
        verify(enderecoService, never()).save(anyLong(), any());
    }

    @Test
    @DisplayName("Deve lançar DatabaseOperationException se o save falhar")
    void shouldThrowExceptionWhenCreateFails() {
        when(usuarioRepository.findByEmail(adminRequestDTO.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("senha_criptografada");
        when(adminRepository.save(any(Admin.class))).thenThrow(new RuntimeException("Falha de persistência"));

        assertThrows(DatabaseOperationException.class, () -> {
            adminService.create(adminRequestDTO);
        });

        verify(adminRepository, times(1)).save(any(Admin.class));
        verify(enderecoService, never()).save(anyLong(), any());
    }

    @Test
    @DisplayName("Deve retornar AdminResponseDTO ao buscar por ID existente")
    void shouldReturnAdminById() {
        when(adminRepository.findById(adminId)).thenReturn(Optional.of(adminMock));

        AdminResponseDTO resultado = adminService.getById(adminId);

        assertNotNull(resultado);
        assertEquals(adminId, resultado.getId());
        verify(adminRepository, times(1)).findById(adminId);
    }

    @Test
    @DisplayName("Deve retornar lista de Admins no getAll")
    void shouldReturnAllAdmins() {
        when(adminRepository.findAll()).thenReturn(List.of(adminMock));

        List<AdminResponseDTO> resultados = adminService.getAll();

        assertFalse(resultados.isEmpty());
        assertEquals(1, resultados.size());
        verify(adminRepository, times(1)).findAll();
    }


    @Test
    @DisplayName("Deve atualizar nome e criptografar nova senha com sucesso")
    void shouldUpdateAdminSuccessfully() {
        Admin adminExistente = new Admin();
        adminExistente.setId(adminId);
        adminExistente.setNome("Nome Antigo");
        adminExistente.setSenha("senha_antiga_hash");

        AdminRequestDTO updateDTO = new AdminRequestDTO("Novo Nome", "novo@email.com", "novaSenha123", null);

        when(adminRepository.findById(adminId)).thenReturn(Optional.of(adminExistente));
        when(usuarioRepository.findByEmail(updateDTO.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("novaSenha123")).thenReturn("nova_senha_hash");
        when(adminRepository.save(any(Admin.class))).thenReturn(adminExistente);

        adminService.update(updateDTO, adminId);


        assertEquals("Novo Nome", adminExistente.getNome());
        assertEquals("nova_senha_hash", adminExistente.getSenha());


        verify(usuarioRepository, times(1)).findByEmail(updateDTO.getEmail());
        verify(passwordEncoder, times(1)).encode("novaSenha123");
        verify(adminRepository, times(1)).save(adminExistente);
    }


    @Test
    @DisplayName("Deve deletar Admin com sucesso quando ID existir")
    void shouldDeleteAdminSuccessfully() {
        when(adminRepository.findById(adminId)).thenReturn(Optional.of(adminMock));

        adminService.delete(adminId);


        verify(adminRepository, times(1)).deleteById(adminId);
    }

    @Test
    @DisplayName("Deve lançar UserNotFoundException se o ID não existir ao deletar")
    void shouldThrowExceptionWhenDeleteIdNotFound() {
        when(adminRepository.findById(adminId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            adminService.delete(adminId);
        });

        verify(adminRepository, never()).deleteById(anyLong());
    }
}