package br.com.fiap.techchallenge.services.impl;

import br.com.fiap.techchallenge.controllers.dto.ClienteRequestDTO;
import br.com.fiap.techchallenge.controllers.dto.ClienteResponseDTO;
import br.com.fiap.techchallenge.controllers.dto.ClienteEmailDTO;
import br.com.fiap.techchallenge.domain.Cliente;
import br.com.fiap.techchallenge.repositories.ClienteRepository;
import br.com.fiap.techchallenge.services.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository clienteRepository;


    @Override
    public List<ClienteResponseDTO> getAll() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(ClienteResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteResponseDTO getById(Long id) {
        return clienteRepository.findByIdClienteDTO(id).orElseThrow();
    }

    @Override
    public ClienteResponseDTO create(ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(clienteRequestDTO, cliente);
        cliente.setDataCriacao(Instant.now());
        cliente.setDataUltimaAlteracao(Instant.now());
        return getClienteResponseDTO(cliente);
    }

    @Override
    public ClienteResponseDTO update(ClienteRequestDTO clienteRequestDTO, Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "cliente não encontrado"));
        cliente.setNome(clienteRequestDTO.getNome());
        cliente.setEmail(clienteRequestDTO.getEmail());
        cliente.setSenha(clienteRequestDTO.getSenha());
        cliente.setDataUltimaAlteracao(Instant.now());

        return getClienteResponseDTO(cliente);
    }

    @Override
    public Cliente updateEmail(ClienteEmailDTO clienteEmailDTO, Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow();
        BeanUtils.copyProperties(clienteEmailDTO, cliente);
        return clienteRepository.save(cliente);
    }

    @Override
    public void delete(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow();
        clienteRepository.delete(cliente);
    }

    private ClienteResponseDTO getClienteResponseDTO(Cliente cliente) {
        clienteRepository.save(cliente);
        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO();
        clienteResponseDTO.setId(cliente.getId());
        clienteResponseDTO.setNome(cliente.getNome());
        clienteResponseDTO.setEmail(cliente.getEmail());
        clienteResponseDTO.setDataCriacao(cliente.getDataCriacao());
        clienteResponseDTO.setDataUltimaAlteracao(cliente.getDataUltimaAlteracao());
        return clienteResponseDTO;
    }
}
