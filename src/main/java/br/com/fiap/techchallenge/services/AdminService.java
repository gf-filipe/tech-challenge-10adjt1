package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.controllers.dto.AdminRequestDTO;
import br.com.fiap.techchallenge.controllers.dto.AdminResponseDTO;

import java.util.List;

public interface AdminService {
    List<AdminResponseDTO> getAll();

    AdminResponseDTO getById(Long id);

    AdminResponseDTO create(AdminRequestDTO AdminRequestDTO);

    AdminResponseDTO update(AdminRequestDTO adminRequestDTO, Long id);

    void delete(Long id);
}
