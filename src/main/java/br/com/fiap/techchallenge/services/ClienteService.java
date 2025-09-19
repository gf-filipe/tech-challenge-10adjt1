package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.controllers.dto.ClienteDTO;
import br.com.fiap.techchallenge.controllers.dto.ClienteEmailDTO;
import br.com.fiap.techchallenge.domain.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    List<ClienteDTO> getAll();

    ClienteDTO getById(Long id);

    Cliente create(ClienteDTO clienteDTO);

    Cliente update(ClienteDTO clienteDTO, Long id);

    Cliente updateEmail(ClienteEmailDTO clienteEmailDTO, Long id);

    void delete(Long id);
}
