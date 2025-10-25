package br.com.fiap.techchallenge.services.impl;

import br.com.fiap.techchallenge.controllers.dto.AdminRequestDTO;
import br.com.fiap.techchallenge.controllers.dto.AdminResponseDTO;
import br.com.fiap.techchallenge.controllers.dto.EnderecoRequestDTO;
import br.com.fiap.techchallenge.domain.Admin;
import br.com.fiap.techchallenge.domain.Endereco;
import br.com.fiap.techchallenge.exceptions.UserNotFoundException;
import br.com.fiap.techchallenge.repositories.AdminRepository;
import br.com.fiap.techchallenge.services.AdminService;
import br.com.fiap.techchallenge.services.EnderecoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final EnderecoService enderecoService;

    @Override
    public List<AdminResponseDTO> getAll() {
        return adminRepository.findAll().stream()
                .map(AdminResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public AdminResponseDTO getById(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Admin com ID " + id + " não encontrado."));
        return new AdminResponseDTO(admin);
    }

    @Override
    @Transactional
    public AdminResponseDTO create(AdminRequestDTO adminRequestDTO) {
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminRequestDTO, admin, "endereco");

        admin.setSenha(passwordEncoder.encode(adminRequestDTO.getSenha()));

        admin.setDataCriacao(Instant.now());
        admin.setDataUltimaAlteracao(Instant.now());

        Admin adminSalvo = adminRepository.save(admin);

        if (adminRequestDTO.getEndereco() != null) {
            EnderecoRequestDTO enderecoRequestDTO = new EnderecoRequestDTO();
            BeanUtils.copyProperties(adminRequestDTO.getEndereco(), enderecoRequestDTO);
            enderecoService.save(adminSalvo.getId(), enderecoRequestDTO);
        }
        return new AdminResponseDTO(adminSalvo);
    }

    @Override
    @Transactional
    public AdminResponseDTO update(AdminRequestDTO adminRequestDTO, Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Admin com ID " + id + " não encontrado para atualização."));
        admin.setNome(adminRequestDTO.getNome());
        admin.setEmail(adminRequestDTO.getEmail());

        if (adminRequestDTO.getSenha() != null && !adminRequestDTO.getSenha().isEmpty()) {
            admin.setSenha(passwordEncoder.encode(adminRequestDTO.getSenha()));
        }

        if (adminRequestDTO.getEndereco() != null) {
            Endereco endereco = admin.getEndereco() == null ? new Endereco() : admin.getEndereco();
            BeanUtils.copyProperties(adminRequestDTO.getEndereco(), endereco);
            admin.setEndereco(endereco);
        } else {
            admin.setEndereco(null);
        }

        admin.setDataUltimaAlteracao(Instant.now());
        Admin adminSalvo = adminRepository.save(admin);

        return new AdminResponseDTO(adminSalvo);
    }

    @Override
    public void delete(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Admin com ID " + id + " não encontrado para exclusão."));
        adminRepository.delete(admin);
    }
}