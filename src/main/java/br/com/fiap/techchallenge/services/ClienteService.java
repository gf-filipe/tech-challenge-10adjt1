package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.controllers.dto.ClienteRequestDTO;
import br.com.fiap.techchallenge.controllers.dto.ClienteResponseDTO;
import br.com.fiap.techchallenge.controllers.dto.ClienteEmailDTO;
import br.com.fiap.techchallenge.domain.Cliente;

import java.util.List;

public interface ClienteService {

    List<ClienteResponseDTO> getAll();

    ClienteResponseDTO getById(Long id);

    ClienteResponseDTO create(ClienteRequestDTO clienteRequestDTO);

    ClienteResponseDTO update(ClienteRequestDTO clienteRequestDTO, Long id);

    void delete(Long id);
}
