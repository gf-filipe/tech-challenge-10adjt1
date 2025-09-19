package br.com.fiap.techchallenge.services.impl;

import br.com.fiap.techchallenge.controllers.dto.ClienteDTO;
import br.com.fiap.techchallenge.controllers.dto.ClienteEmailDTO;
import br.com.fiap.techchallenge.domain.Cliente;
import br.com.fiap.techchallenge.repositories.ClienteRepository;
import br.com.fiap.techchallenge.services.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository clienteRepository;


    @Override
    public List<ClienteDTO> getAll() {
        return clienteRepository.findAllClienteDTO();
    }

    @Override
    public ClienteDTO getById(Long id) {
        return clienteRepository.findByIdClienteDTO(id).orElseThrow();
    }

    @Override
    public Cliente create(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(clienteDTO, cliente);
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente update(ClienteDTO clienteDTO, Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow();
        BeanUtils.copyProperties(clienteDTO, cliente);
        return clienteRepository.save(cliente);
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
}
