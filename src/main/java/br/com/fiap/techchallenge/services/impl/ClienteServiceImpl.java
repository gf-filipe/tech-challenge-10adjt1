package br.com.fiap.techchallenge.services.impl;

import br.com.fiap.techchallenge.controllers.dto.ClienteEmailDTO;
import br.com.fiap.techchallenge.controllers.dto.ClienteRequestDTO;
import br.com.fiap.techchallenge.controllers.dto.ClienteResponseDTO;
import br.com.fiap.techchallenge.domain.Cliente;
import br.com.fiap.techchallenge.domain.Endereco;
import br.com.fiap.techchallenge.exceptions.UserNotFoundException;
import br.com.fiap.techchallenge.repositories.ClienteRepository;
import br.com.fiap.techchallenge.services.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<ClienteResponseDTO> getAll() {
        return clienteRepository.findAll().stream()
                .map(ClienteResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteResponseDTO getById(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Cliente não encontrado"));
        return new ClienteResponseDTO(cliente);
    }

    @Override
    public ClienteResponseDTO create(ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(clienteRequestDTO, cliente, "senha", "endereco");

        cliente.setSenha(passwordEncoder.encode(clienteRequestDTO.getSenha()));

        if (clienteRequestDTO.getEndereco() != null) {
            Endereco endereco = new Endereco();
            BeanUtils.copyProperties(clienteRequestDTO.getEndereco(), endereco);
            cliente.setEndereco(endereco);
        }
        cliente.setDataCriacao(Instant.now());
        cliente.setDataUltimaAlteracao(Instant.now());
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return new ClienteResponseDTO(clienteSalvo);
    }

    @Override
    public ClienteResponseDTO update(ClienteRequestDTO clienteRequestDTO, Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Cliente não encontrado"));

        cliente.setNome(clienteRequestDTO.getNome());
        cliente.setEmail(clienteRequestDTO.getEmail());

        if (clienteRequestDTO.getSenha() != null && !clienteRequestDTO.getSenha().isEmpty()) {
            cliente.setSenha(passwordEncoder.encode(clienteRequestDTO.getSenha()));
        }

        if (clienteRequestDTO.getEndereco() != null) {
            Endereco endereco = cliente.getEndereco() == null ? new Endereco() : cliente.getEndereco();
            BeanUtils.copyProperties(clienteRequestDTO.getEndereco(), endereco);
            cliente.setEndereco(endereco);
        } else {
            cliente.setEndereco(null);
        }

        cliente.setDataUltimaAlteracao(Instant.now());
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return new ClienteResponseDTO(clienteSalvo);
    }

    @Override
    public Cliente updateEmail(ClienteEmailDTO clienteEmailDTO, Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Cliente não encontrado"));
        BeanUtils.copyProperties(clienteEmailDTO, cliente);
        return clienteRepository.save(cliente);
    }

    @Override
    public void delete(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Cliente não encontrado"));
        clienteRepository.delete(cliente);
    }
}